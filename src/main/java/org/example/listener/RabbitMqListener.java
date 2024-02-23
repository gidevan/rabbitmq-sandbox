package org.example.listener;

import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@Component
@Slf4j
@ConditionalOnProperty(name = "connection-type", havingValue = "consumer")
public class RabbitMqListener {

    private static final int MAX_PAUSE = 15;

    private static final int ERROR_THRESHOLD = 5000;

    private String applicationId;

    private RabbitTemplate rabbitTemplate;

    public RabbitMqListener(@Value("${application-id}") String applicationId, RabbitTemplate rabbitTemplate) {
        this.applicationId = applicationId;
        this.rabbitTemplate = rabbitTemplate;
    }


    @RabbitListener(queues = {"simpleQueue"})
    public void receive(String message) {
        log.info("Application [{}] receives message:  [{}]", applicationId, message);
        processMessage(message);
    }

    @RabbitListener(queues = {"simpleTtlQueue"})
    public void receiveTtl(Message message) {
        var body = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("Application [{}] receives message:  [{}] from ttlQueue", applicationId, message);
        log.info("Body: {}", body);
        processMessage(body);
    }

    private void processMessage(String message) {
        log.info("Process message [{}] ...", message);
        var randomValue = new Random().nextInt(MAX_PAUSE) * 1000;
        try {

            Thread.sleep(randomValue);

        } catch (Exception e) {
            log.error("Error processing message [{}]", message);
        }
        if (randomValue > ERROR_THRESHOLD) {
            throw new IllegalStateException("Can't process message [" + message + "] in " + randomValue);
        }
        log.info("Message [{}] processed", message);
    }
}
