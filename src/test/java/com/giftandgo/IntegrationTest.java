package com.giftandgo;

import com.giftandgo.geolocation.service.RequestSentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static com.giftandgo.fileprocessor.utils.FileContentStubValues.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"ip-check.api-url=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock(port = 0)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RequestSentRepository requestSentRepository;

    @BeforeEach
    void setUp() {
        requestSentRepository.deleteAll();
    }

    @Test
    public void shouldProcessFileSuccessfully() {
        stubSuccessfullItalianIpAddressResponse();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/files/process", VALID_CONTENT, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(responseEntity.getBody()).isEqualToIgnoringWhitespace(EXPECTED_VALID_JSON_CONTENT);
        assertThat(requestSentRepository.findAll()).hasSize(1);
    }

    @Test
    public void shouldFailDueToValidation() {
        stubSuccessfullItalianIpAddressResponse();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/files/process", INVALID_CONTENT, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(requestSentRepository.findAll()).hasSize(1);
    }

    @Test
    void shouldFailIfClientIpAddressIsBlocked() {
        stubSuccessfulChineseIpAddressResponse();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/files/process", VALID_CONTENT, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(403));
        assertThat(requestSentRepository.findAll()).hasSize(1);
    }

    @Test
    void shouldFailIfClientIpAddressIsInvalid() {
        stubUnsuccessfulResponse();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/files/process", VALID_CONTENT, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(requestSentRepository.findAll()).hasSize(1);
    }

    private static void stubSuccessfulChineseIpAddressResponse() {
        stubFor(get(urlPathEqualTo("/json/127.0.0.1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                //language=JSON
                                """
                                {
                                  "query": "24.48.0.1",
                                  "status": "success",
                                  "country": "China",
                                  "countryCode": "CN",
                                  "isp": "Le Groupe Videotron Ltee"
                                }""")));
    }

    private static void stubSuccessfullItalianIpAddressResponse() {
        stubFor(get(urlPathEqualTo("/json/127.0.0.1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                //language=JSON
                                """
                                {
                                  "query": "24.48.0.1",
                                  "status": "success",
                                  "country": "Italy",
                                  "countryCode": "IT",
                                  "isp": "Telecom"
                                }""")));
    }

    private static void stubUnsuccessfulResponse() {
        stubFor(get(urlPathEqualTo("/json/127.0.0.1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                //language=JSON
                                """
                                {
                                  "query": "127.0.0.1",
                                  "message": "reserved range",
                                  "status": "fail"
                                }""")));
    }
}
