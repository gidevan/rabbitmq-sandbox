package org.example.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMqProducer {

    private String applicationId;
    private RabbitTemplate rabbitTemplate;
    private Queue queue;

    public RabbitMqProducer(@Value("${application-id}") String applicationId, RabbitTemplate rabbitTemplate, Queue queue) {
        this.applicationId = applicationId;
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    public void sendMessage(String message) {
        var msg = applicationId + ":" + message;
        rabbitTemplate.convertAndSend(queue.getName(), msg);
    }
}
