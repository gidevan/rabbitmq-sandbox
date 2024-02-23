package org.example.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(name = "connection-type", havingValue = "producer")
public class RabbitMqProducer {

    private final String applicationId;
    private final RabbitTemplate rabbitTemplate;

    private final MessagePostProcessor messagePostProcessor;
    private final Queue queue;

    private final Queue ttlQueue;

    public RabbitMqProducer(@Value("${application-id}") String applicationId, RabbitTemplate rabbitTemplate,
                            MessagePostProcessor messagePostProcessor,
                            Queue queue,
                            Queue ttlQueue) {
        this.applicationId = applicationId;
        this.rabbitTemplate = rabbitTemplate;
        this.messagePostProcessor = messagePostProcessor;
        this.queue = queue;
        this.ttlQueue = ttlQueue;
    }

    public void sendMessage(String message) {
        var msg = applicationId + ":" + message;
        rabbitTemplate.convertAndSend(queue.getName(), msg);
    }

    public void sendMessageConfigured(String message) {
        log.info("Send configured ...");
        var msg = applicationId + ":" + message;
        rabbitTemplate.convertAndSend(ttlQueue.getName(), (Object) msg, messagePostProcessor);
    }
}
