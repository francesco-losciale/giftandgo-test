package com.giftandgo.geolocation.service;

import com.giftandgo.geolocation.client.IpCheckClient;
import com.giftandgo.geolocation.client.IpCheckResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GeolocationService {
    private final IpCheckClient client;
    private final RequestSentRepository requestSentRepository;

    private final List<String> blockedCountryCodeList = List.of("CN", "US", "ES");
    private final List<String> blockedIspList = List.of("AWS", "Azure", "GCP");

    public GeolocationService(IpCheckClient client, RequestSentRepository requestSentRepository) {
        this.client = client;
        this.requestSentRepository = requestSentRepository;
    }

    public void validateIpAddress(String ipAddress) throws InvalidIpAddressException, IOException, URISyntaxException, IpCheckFailureException {
        IpCheckResponse ipCheckResponse = client.check(ipAddress);
        RequestSent requestSent = RequestSent.builder()
                .uri("URI") //TODO
                .createdAt(LocalDateTime.now())
                .httpStatusCode(200) // TODO
                .ipAddress(ipAddress)
                .countryCode(ipCheckResponse.getCountryCode())
                .ipProvider(ipCheckResponse.getIsp())
                .responseTime(0) //TODO
                .build();
        requestSentRepository.save(requestSent);
        if (ipCheckResponse.getStatus().equals("fail")) {
            throw new IpCheckFailureException();
        }
        if (blockedCountryCodeList.contains(ipCheckResponse.getCountryCode()) ||
                blockedIspList.contains(ipCheckResponse.getIsp())) {
            throw new InvalidIpAddressException();
        }
    }
}
