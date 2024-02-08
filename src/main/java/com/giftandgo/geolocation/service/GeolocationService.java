package com.giftandgo.geolocation.service;

import com.giftandgo.geolocation.client.IpCheckClient;
import com.giftandgo.geolocation.client.IpCheckResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class GeolocationService {
    private final IpCheckClient client;

    private final List<String> blockedCountryCodeList = List.of("CN", "US", "ES");
    private final List<String> blockedIspList = List.of("AWS", "Azure", "GCP");

    public GeolocationService(IpCheckClient client) {
        this.client = client;
    }

    public void validateIpAddress(String ipAddress) throws InvalidIpAddressException, IOException, URISyntaxException {
        IpCheckResponse ipCheckResponse = client.check(ipAddress);
        if (blockedCountryCodeList.contains(ipCheckResponse.getCountryCode()) ||
                blockedIspList.contains(ipCheckResponse.getIsp())) {
            throw new InvalidIpAddressException();
        }
    }
}
