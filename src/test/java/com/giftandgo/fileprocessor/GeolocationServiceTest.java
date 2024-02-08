package com.giftandgo.fileprocessor;

import com.giftandgo.geolocation.client.IpCheckClient;
import com.giftandgo.geolocation.client.IpCheckResponse;
import com.giftandgo.geolocation.service.GeolocationService;
import com.giftandgo.geolocation.service.InvalidIpAddressException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeolocationServiceTest {

    @Test
    void shouldValidateIpAddress() throws Exception {
        IpCheckClient client = mock(IpCheckClient.class);
        when(client.check(any())).thenReturn(new IpCheckResponse("IT", "A random ISP"));
        GeolocationService geolocationService = new GeolocationService(client);

        geolocationService.validateIpAddress("127.0.0.1");
    }

    @ParameterizedTest
    @ValueSource(strings={"CN", "ES", "US"})
    void shouldFailWhitBlockedCountry(String blockedCountryCode) throws Exception {
        IpCheckClient client = mock(IpCheckClient.class);
        IpCheckResponse response = new IpCheckResponse(blockedCountryCode, "A random ISP");
        when(client.check(any())).thenReturn(response);
        GeolocationService geolocationService = new GeolocationService(client);

        assertThrows(InvalidIpAddressException.class, () -> {
            geolocationService.validateIpAddress("127.0.0.1");
        });
    }

    @ParameterizedTest
    @ValueSource(strings={"AWS", "Azure", "GCP"})
    void shouldFailWhitBlockedISP(String blockedISP) throws Exception {
        IpCheckClient client = mock(IpCheckClient.class);
        IpCheckResponse response = new IpCheckResponse("A random country", blockedISP);
        when(client.check(any())).thenReturn(response);
        GeolocationService geolocationService = new GeolocationService(client);

        assertThrows(InvalidIpAddressException.class, () -> {
            geolocationService.validateIpAddress("127.0.0.1");
        });
    }
}
