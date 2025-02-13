package com.bigwork.bigwork_apitest.common;


import com.bigwork.bigwork_apitest.service.ReceiveChatMessage;
import com.bigwork.bigwork_apitest.service.WebsocketService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;

@Component
public class kafkaConsumer {
    @Resource
    private KafkaConsumer<String, String> consumer;
    @Resource
    private ReceiveChatMessage receiveChatMessage;
 
    @PostConstruct
    private void startConsuming(){
        consumer.subscribe(Collections.singletonList("chat-list-topic"));
        new Thread(() -> {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    //处理消费逻辑
                    switch(record.key()){
                        case "chat-list":
                            receiveChatMessage.receiveChatDetailDo(record.value());
                            break;
                    }


                }
            }
        }).start();
    }



}
