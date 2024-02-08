package com.giftandgo.fileprocessor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static com.giftandgo.fileprocessor.FileContentStubValues.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"ip-check.api-url=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock(port = 0)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void shouldProcessFileSuccessfully() {
        stubSuccessfulIpCheckResponse();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/files/process", VALID_CONTENT, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(responseEntity.getBody()).isEqualToIgnoringWhitespace(EXPECTED_VALID_JSON_CONTENT);
    }

    @Test
    public void shouldFailDueToValidation() {
        stubSuccessfulIpCheckResponse();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/files/process", INVALID_CONTENT, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
    }

    @Test
    void shouldFailDueToInvalidGeolocation() {
        stubUnsuccessfulIpCheckResponse();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/files/process", VALID_CONTENT, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(403));
    }

    private static void stubUnsuccessfulIpCheckResponse() {
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

    private static void stubSuccessfulIpCheckResponse() {
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
}
