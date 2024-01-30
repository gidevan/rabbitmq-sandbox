package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.service.RabbitMqService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@ConditionalOnProperty(name = "connection-type", havingValue = "producer")
@AllArgsConstructor
public class ProducerController {

    private RabbitMqService rabbitMqService;

    @GetMapping("/send/{message}")
    public String sendMessage(@PathVariable String message) {
        rabbitMqService.sendMessage(message);
        return "send: " + message + " at " + LocalDateTime.now();
    }

    @GetMapping("/send/templates/{count}")
    public String sendMessages(@PathVariable Integer count) {
        rabbitMqService.sendMessages(count);
        return "send: " + count + " messages at " + LocalDateTime.now();
    }

    @PostMapping("/send/configured/{count}")
    public String sendConfiguredMessages(@PathVariable Integer count) {
        rabbitMqService.sendConfiguredMessages(count);
        return "send configured: " + count + " messages at " + LocalDateTime.now();
    }


}
