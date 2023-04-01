package com.example.demo.MQ.Listener;

import com.example.demo.Service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class MyTransactionListener implements TransactionListener {
    @Autowired
    TestService testService;

    //执行本地事务
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        LocalTransactionState state = LocalTransactionState.UNKNOW;
        try{

            //执行数据库操作
            boolean isCommitted = testService.doTransfer();
            if (isCommitted){
                state = LocalTransactionState.COMMIT_MESSAGE;
            }else {
                state = LocalTransactionState.ROLLBACK_MESSAGE;
            }

        }catch (Exception e){
            e.printStackTrace();
            state = LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return state;
    }

    //检查本地事务,这个主要是用来防止
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        return null;
    }
}
