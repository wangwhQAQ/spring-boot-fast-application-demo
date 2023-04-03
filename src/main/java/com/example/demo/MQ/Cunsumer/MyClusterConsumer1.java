package com.example.demo.MQ.Cunsumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

@Slf4j
public class MyClusterConsumer1 {
    private DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer("cluster-consumer-group1");

    public MyClusterConsumer1() {
        this.pushConsumer.setNamesrvAddr("192.168.190.67:9876");
        //设置集群模式
        this.pushConsumer.setMessageModel(MessageModel.CLUSTERING);


    }

    public static void main(String[] args) throws MQClientException {
        MyClusterConsumer1 clusterConsumer1 = new MyClusterConsumer1();
        clusterConsumer1.pushConsumer.subscribe("TEST1", "*");

        clusterConsumer1.pushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt msg:list){
                    System.out.println(msg.toString());
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        clusterConsumer1.pushConsumer.start();
    }
}
