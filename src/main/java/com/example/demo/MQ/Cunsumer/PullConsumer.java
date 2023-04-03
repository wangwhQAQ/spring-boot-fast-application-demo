package com.example.demo.MQ.Cunsumer;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class PullConsumer extends DefaultLitePullConsumer {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        PullConsumer pullConsumer = new PullConsumer();
        pullConsumer.setNamesrvAddr("192.168.190.67:9876");
        pullConsumer.subscribe("pull-topic", "*");
        pullConsumer.start();

        while (true){
            Thread.sleep(10000);
            //调用pull来拉取数据，每次拉10条，如果不够10条就拉完
            List<MessageExt> msg = pullConsumer.poll();
        }
    }
}
