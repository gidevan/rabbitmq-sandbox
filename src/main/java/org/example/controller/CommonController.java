package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.service.ChainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CommonController {

    private ChainService chainService;

    @GetMapping("/simple-request")
    public String simpleRequest() throws InterruptedException {
        return chainService.getSimpleResponse();
    }

}
