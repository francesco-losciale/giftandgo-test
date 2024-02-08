package com.giftandgo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giftandgo.geolocation.client.FindMyIpAddressClient;
import com.giftandgo.geolocation.service.GeolocationService;
import com.giftandgo.fileprocessor.model.Output;
import com.giftandgo.fileprocessor.service.FileProcessorService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FileProcessorController {

    private final GeolocationService geolocationService;
    private final FileProcessorService fileProcessorService;

    private final FindMyIpAddressClient findMyIpAddressClient;
    private final ObjectMapper objectMapper;

    public FileProcessorController(GeolocationService geolocationService, FileProcessorService fileProcessorService, FindMyIpAddressClient findMyIpAddressClient, ObjectMapper objectMapper) {
        this.geolocationService = geolocationService;
        this.fileProcessorService = fileProcessorService;
        this.findMyIpAddressClient = findMyIpAddressClient;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/files/process")
    public String processFile(@Validated @RequestBody String content) throws Exception {
        String ipAddress = findMyIpAddressClient.getIp();
        geolocationService.validateIpAddress(ipAddress);
        List<Output> outputList = fileProcessorService.generateOutput(content);
        return objectMapper.writeValueAsString(outputList);
    }
}
