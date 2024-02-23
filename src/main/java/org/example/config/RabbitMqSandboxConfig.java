package org.example.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqSandboxConfig {

    private static final String QUEUE_NAME = "simpleQueue";
    private static final String QUEUE_TTL_NAME = "simpleTtlQueue";
    private static final String QUEUE_DLQ_NAME = QUEUE_TTL_NAME + ".dlq";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Queue ttlQueue() {
        //return QueueBuilder.nonDurable(QUEUE_TTL_NAME)
        return QueueBuilder.durable(QUEUE_TTL_NAME)
                //.withArgument("x-dead-letter-exchange", "")
                //.withArgument("x-dead-letter-routing-key", QUEUE_DLQ_NAME)
                //.ttl(3000)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(QUEUE_TTL_NAME).build();
    }
}
