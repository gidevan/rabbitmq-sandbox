package org.example.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqSandboxConfig {

    private static final String QUEUE_NAME = "simpleQueue";
    private static final String QUEUE_DLQ_NAME = QUEUE_NAME + ".dlq";

    @Bean
    public Queue queue() {
        //return new Queue(QUEUE_NAME, false);

        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_DLQ_NAME)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(QUEUE_DLQ_NAME, false);
    }
}
