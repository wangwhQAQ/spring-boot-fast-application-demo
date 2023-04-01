package com.example.demo.MQ.MessageQueueSelector;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

/**
 * @author DELL
 */
@Slf4j
public class PartitionQueueSelector implements MessageQueueSelector {
    @Override
    public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
        Integer key = Integer.valueOf(message.getKeys());
        log.info("key：" + key);
        int size = list.size();
        int select = key % size;
        log.info("选择的队列:" + select);

        return list.get(select);
    }
}
