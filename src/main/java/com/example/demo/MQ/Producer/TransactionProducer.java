package com.example.demo.MQ.Producer;

import com.example.demo.MQ.Listener.MyTransactionListener;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TransactionProducer {
    // 事务的处理逻辑和普通的处理逻辑是不一样的，所以组要分开
    public TransactionMQProducer transactionMQProducer ;

    private ThreadPoolExecutor threadPoolExecutor ;
    TransactionProducer(){
        transactionMQProducer = new TransactionMQProducer("TRANSACTION_QUEUE");
        transactionMQProducer.setNamesrvAddr("192.168.190.67:9876");
        //设置一个连接池，用于回查本地事务的状态
        threadPoolExecutor = new ThreadPoolExecutor(
                3,
                6,
                1000,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(20),
                new DefaultThreadFactory("TRANSACTION_THREAD:"),
                new ThreadPoolExecutor.DiscardPolicy()
        );

        transactionMQProducer.setExecutorService(threadPoolExecutor);
    }

    public static void main(String[] args) throws MQClientException {
        TransactionProducer transactionProducer = new TransactionProducer();

        TransactionListener listener = new MyTransactionListener();
        transactionProducer.transactionMQProducer.setTransactionListener(listener);

        transactionProducer.transactionMQProducer.start();

        Message msg = new Message("TRANSACTION_QUEUE", "20230401", "transaction-key" , "测试事务消息".getBytes());

        transactionProducer.transactionMQProducer.sendMessageInTransaction(msg, "附加参数?");
    }
}
