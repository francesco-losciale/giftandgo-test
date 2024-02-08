package com.giftandgo.geolocation.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@Component
public class IpCheckClient {
    private final IpCheckConfig config;

    private final RestTemplate restTemplate;

    public IpCheckClient(RestTemplate restTemplate, IpCheckConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    public IpCheckResponse check(String ipAddress) throws IOException, URISyntaxException {
        return restTemplate.getForObject(new URL(config.getApiUrl() + "/json/" + ipAddress).toURI(), IpCheckResponse.class);
    }
}
