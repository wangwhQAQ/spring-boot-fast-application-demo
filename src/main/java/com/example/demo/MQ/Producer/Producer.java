package com.example.demo.MQ.Producer;

import com.example.demo.MQ.MessageQueueSelector.PartitionQueueSelector;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;

@Slf4j
public class Producer {
    static DefaultMQProducer producer = new DefaultMQProducer("testProducer1");

    Producer(){
        producer.setNamesrvAddr("192.168.190.67:9876");
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        Producer producer1 = new Producer();
//        Integer id = 6;
//        Message partition = new Message("TEST1", "20230401", id.toString(), "partition message ".getBytes(RemotingHelper.DEFAULT_CHARSET)  );
//        partition.setDelayTimeLevel(5);
//        producer1.sendPartitionMessage(partition);

        try{
            producer.start();

            for (int i = 0; i < 10; i++) {
                String data = "\"text\" : \" 测试消息: \"" +i;
                Message message = new Message("TEST1", "20230330", data.getBytes());
                message.setKeys(String.valueOf(i));
                SendResult result = producer.send(message);
                Thread.sleep(1000);

                System.out.println(result.toString());

            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            producer.shutdown();
        }
    }

    public void sendPartitionMessage(Message message){
        try{
            producer.start();

            SendResult sendResult = producer.send(message, new PartitionQueueSelector(), null);

            log.info("消息已发送:" + sendResult.toString());


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            producer.shutdown();
        }
    }
}
