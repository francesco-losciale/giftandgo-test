package com.giftandgo.geolocation;

import com.giftandgo.geolocation.client.IpCheckClient;
import com.giftandgo.geolocation.client.IpCheckResponse;
import com.giftandgo.geolocation.service.GeolocationService;
import com.giftandgo.geolocation.service.InvalidIpAddressException;
import com.giftandgo.geolocation.service.IpCheckFailureException;
import com.giftandgo.geolocation.service.RequestSentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GeolocationServiceTest {

    @Mock
    private RequestSentRepository requestSentRepository;

    @Mock
    private IpCheckClient ipCheckClient;

    @InjectMocks
    private GeolocationService geolocationService;

    @Test
    void shouldValidateIpAddress() throws Exception {
        when(ipCheckClient.check(any())).thenReturn(new IpCheckResponse("IT", "A random ISP", "success"));
        GeolocationService geolocationService = new GeolocationService(ipCheckClient, requestSentRepository);

        geolocationService.validateIpAddress("127.0.0.1");
    }

    @ParameterizedTest
    @ValueSource(strings={"CN", "ES", "US"})
    void shouldFailWhitBlockedCountry(String blockedCountryCode) throws Exception {
        IpCheckResponse response = new IpCheckResponse(blockedCountryCode, "A random ISP", "success");
        when(ipCheckClient.check(any())).thenReturn(response);
        GeolocationService geolocationService = new GeolocationService(ipCheckClient, requestSentRepository);

        assertThrows(InvalidIpAddressException.class, () -> geolocationService.validateIpAddress("127.0.0.1"));
    }

    @ParameterizedTest
    @ValueSource(strings={"AWS", "Azure", "GCP"})
    void shouldFailWhitBlockedISP(String blockedISP) throws Exception {
        IpCheckResponse response = new IpCheckResponse("A random country", blockedISP, "success");
        when(ipCheckClient.check(any())).thenReturn(response);
        GeolocationService geolocationService = new GeolocationService(ipCheckClient, requestSentRepository);

        assertThrows(InvalidIpAddressException.class, () -> geolocationService.validateIpAddress("127.0.0.1"));
    }

    @Test
    void shouldFailWhenIpCheckIsUnsuccessful() throws Exception {
        IpCheckResponse response = new IpCheckResponse("A random country", "A random ISP", "fail");
        when(ipCheckClient.check(any())).thenReturn(response);

        assertThrows(IpCheckFailureException.class, () -> geolocationService.validateIpAddress("127.0.0.1"));
    }
}
