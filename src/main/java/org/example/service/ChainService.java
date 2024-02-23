package org.example.service;

import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@Slf4j
public class ChainService {

    private final RestTemplate restTemplate;

    private final Tracer tracer;

    private final String remoteUrl;

    public ChainService(RestTemplate restTemplate, Tracer tracer, @Value("${remote-url}") String remoteUrl) {
        this.restTemplate = restTemplate;
        this.tracer = tracer;
        this.remoteUrl = remoteUrl;
    }

    public String getRemoteResponse() {
        log.info("Remote call of : {}", remoteUrl);
        var producerSpan = tracer.buildSpan("producer" + UUID.randomUUID())
                .start();
        var responseEntity = restTemplate.getForEntity(remoteUrl, String.class);
        var body = responseEntity.getBody();
        var traceId = producerSpan.context().toTraceId();

        var spanId = producerSpan.context().toSpanId();
        log.info("Remote Response. TraceId: {}, SpanId: {}", traceId, spanId);
        log.info("Body: {}", body);
        producerSpan.finish();
        return "remoteResponse: " + body;
    }

    public String getSimpleResponse() throws InterruptedException {
        log.info("return simple-response");
        var simpleResponseSpan = tracer.activeSpan()
                .setTag("simple-response-tag", "consumer-" + UUID.randomUUID());
        Thread.sleep(2000L);
        var traceId = simpleResponseSpan.context().toTraceId();
        var spanId = simpleResponseSpan.context().toSpanId();

        log.info("Simple Response. TraceId: {}, SpanId: {}", traceId, spanId);
        return "simple-response";
    }


}
