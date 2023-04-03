package com.example.demo.MQ.Listener;

import com.example.demo.Service.Impl.TestServiceImpl;
import com.example.demo.Service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@Slf4j
public class MyTransactionListener implements TransactionListener {

    //执行本地事务
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        LocalTransactionState state = LocalTransactionState.UNKNOW;
        TestService testService = new TestServiceImpl();
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

    //检查本地事务,这个主要是用来防止，提交commit消息的时候，网络出现问题，队列里的消息就一直处于half状态，这个函数作用就是，使用消息队列发送给你的消息，去反查是否执行成功
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        String key = messageExt.getKeys();
        //执行反查操作，我们这里假设一开始的数据库操作执行失败
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }
}
