package org.example.config;

import io.jaegertracing.Configuration;
import io.opentracing.Tracer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@org.springframework.context.annotation.Configuration
public class CommonConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }

    //@Bean
    public Tracer tracer() {
        Configuration.SamplerConfiguration samplerConfig = new
                Configuration.SamplerConfiguration()
                .withType("const").withParam(1);
        return Configuration.fromEnv("relay-service")
                .withSampler(samplerConfig).getTracer();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

}
