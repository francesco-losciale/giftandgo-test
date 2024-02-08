package com.giftandgo.geolocation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giftandgo.geolocation.client.IpCheckResponse;
import com.giftandgo.geolocation.service.RequestSent;
import com.giftandgo.geolocation.service.RequestSentRepository;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

public class RequestSentLogger implements ClientHttpRequestInterceptor {

    private final ObjectMapper objectMapper;

    private final RequestSentRepository requestSentRepository;

    public RequestSentLogger(RequestSentRepository requestSentRepository, ObjectMapper objectMapper) {
        this.requestSentRepository = requestSentRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        Instant start = Instant.now();
        ClientHttpResponse response = execution.execute(request, body);
        Duration responseTime = Duration.between(start, Instant.now());
        URI uri = request.getURI();
        HttpStatusCode statusCode = response.getStatusCode();
        IpCheckResponse ipCheckResponse = objectMapper.readValue(new String(response.getBody().readAllBytes()), IpCheckResponse.class);
        RequestSent requestSent = createRequestSentObject(request, responseTime, uri, statusCode, ipCheckResponse);
        requestSentRepository.save(requestSent);
        return response;
    }

    private static RequestSent createRequestSentObject(HttpRequest request, Duration responseTime, URI uri, HttpStatusCode statusCode, IpCheckResponse ipCheckResponse) {
        return RequestSent.builder()
                .uri(uri.toString())
                .createdAt(LocalDateTime.now())
                .httpStatusCode(statusCode.value())
                .ipAddress(request.getURI().getHost())
                .countryCode(ipCheckResponse.getCountryCode())
                .ipProvider(ipCheckResponse.getIsp())
                .responseTime(responseTime.toMillis())
                .build();
    }
}
