package org.example.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.producer.RabbitMqProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "connection-type", havingValue = "producer")
public class RabbitMqService {

    private static final String MESSAGE_TEMPLATE = "Rabbit_Message_";

    private RabbitMqProducer rabbitMqProducer;

    public void sendMessage(String message) {
        rabbitMqProducer.sendMessage(message);
    }


    public void sendMessages(Integer count) {
        log.info("Send {} messages like {}", count, MESSAGE_TEMPLATE);
        for(int i = 0; i < count; i++) {
            rabbitMqProducer.sendMessage(MESSAGE_TEMPLATE + i);
        }
    }

    public void sendConfiguredMessages(Integer count) {
        log.info("Send configured {} messages like {}", count, MESSAGE_TEMPLATE);
        for(int i = 0; i < count; i++) {
            rabbitMqProducer.sendMessageConfigured(MESSAGE_TEMPLATE + i);
        }
    }
}
