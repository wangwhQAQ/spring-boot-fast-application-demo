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
public class MyClusterConsumer0 {
    private DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer("cluster-consumer-group");

    public MyClusterConsumer0() {
        this.pushConsumer.setNamesrvAddr("192.168.190.67:9876");
        //设置集群模式(这里设置成广播模式)
        this.pushConsumer.setMessageModel(MessageModel.CLUSTERING);


    }

    public static void main(String[] args) throws MQClientException {
        MyClusterConsumer0 clusterConsumer0 = new MyClusterConsumer0();
        clusterConsumer0.pushConsumer.subscribe("TEST1", "*");

        clusterConsumer0.pushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt msg:list){
                    System.out.println(msg.toString());
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        clusterConsumer0.pushConsumer.start();
    }
}
