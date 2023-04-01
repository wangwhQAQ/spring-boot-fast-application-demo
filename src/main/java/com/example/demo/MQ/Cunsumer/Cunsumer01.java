package com.example.demo.MQ.Cunsumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author DELL
 */
public class Cunsumer01 {

    public static void main(String[] args) {
        //推送方式:服务器推送
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("testPushConsumer");
        consumer.setNamesrvAddr("192.168.190.67:9876");


        try{
            //在生产的时候，设置了tag，所以第二个参数是tag，这里是模糊匹配，比如 2023*  所以参数是Expression
            consumer.subscribe("TEST1", "2023*");
            //创建监听器
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    for (MessageExt msg:list){
                        System.out.println(msg.toString());
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            consumer.start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
