package com.giftandgo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giftandgo.geolocation.interceptor.RequestSentLogger;
import com.giftandgo.geolocation.client.IpCheckConfig;
import com.giftandgo.geolocation.service.RequestSentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@SpringBootApplication
@PropertySource("classpath:application.yaml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RequestSentRepository requestSentRepository, ObjectMapper objectMapper, IpCheckConfig config) {
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        restTemplate.setInterceptors(Collections.singletonList(new RequestSentLogger(requestSentRepository, objectMapper, config)));
        return restTemplate;
    }
}
