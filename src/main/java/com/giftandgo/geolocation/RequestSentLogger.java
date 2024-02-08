package com.giftandgo.geolocation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giftandgo.geolocation.client.IpCheckConfig;
import com.giftandgo.geolocation.client.IpCheckResponse;
import com.giftandgo.geolocation.service.RequestSent;
import com.giftandgo.geolocation.service.RequestSentRepository;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

@Component
public class RequestSentLogger implements ClientHttpRequestInterceptor {

    private final ObjectMapper objectMapper;

    private final RequestSentRepository requestSentRepository;

    private final IpCheckConfig config;

    public RequestSentLogger(RequestSentRepository requestSentRepository, ObjectMapper objectMapper, IpCheckConfig config) {
        this.requestSentRepository = requestSentRepository;
        this.objectMapper = objectMapper;
        this.config = config;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        var start = Instant.now();
        ClientHttpResponse response = execution.execute(request, body);
        if (isRequestForIpCheck(request)) {
            var responseTime = Duration.between(start, Instant.now());
            var uri = request.getURI();
            var statusCode = response.getStatusCode();
            var ipCheckResponse = objectMapper.readValue(new String(response.getBody().readAllBytes()), IpCheckResponse.class);
            var requestSent = createRequestSentObject(request, responseTime, uri, statusCode, ipCheckResponse);
            requestSentRepository.save(requestSent);
        }
        return response;
    }

    private boolean isRequestForIpCheck(HttpRequest request) {
        return request.getURI().toString().contains(config.getApiUrl());
    }

    private static RequestSent createRequestSentObject(HttpRequest request, Duration responseTime, URI uri, HttpStatusCode statusCode, IpCheckResponse ipCheckResponse) {
        return RequestSent.builder()
                .uri(uri.toString())
                .createdAt(LocalDateTime.now())
                .httpStatusCode(statusCode.value())
                .ipAddress(request.getURI().getHost())
                .countryCode(ipCheckResponse.countryCode())
                .ipProvider(ipCheckResponse.isp())
                .responseTime(responseTime.toMillis())
                .build();
    }
}
