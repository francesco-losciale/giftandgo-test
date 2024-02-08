package com.giftandgo.geolocation.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FindMyIpAddressClient {

    private final RestTemplate restTemplate;
    private final FindMyIpAddressConfig config;


    public FindMyIpAddressClient(RestTemplate restTemplate, FindMyIpAddressConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    public String getIp() {
        ResponseEntity<String> response = restTemplate.exchange(this.config.getApiUrl() + "/", HttpMethod.GET, null, String.class);
        return response.getBody();
    }
}
