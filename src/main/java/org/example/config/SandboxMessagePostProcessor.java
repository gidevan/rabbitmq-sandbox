package org.example.config;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SandboxMessagePostProcessor implements MessagePostProcessor {

    private final Integer ttl;

    public SandboxMessagePostProcessor(@Value("${message.ttl}") Integer ttl) {
        this.ttl = ttl;
    }

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties().getHeaders().put("expiration", ttl.toString());
        return message;
    }


}
