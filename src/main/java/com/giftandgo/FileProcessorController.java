package com.giftandgo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giftandgo.geolocation.service.IpCheckFailureException;
import com.giftandgo.geolocation.service.GeolocationService;
import com.giftandgo.geolocation.service.InvalidIpAddressException;
import com.giftandgo.fileprocessor.model.Output;
import com.giftandgo.fileprocessor.service.FileProcessorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class FileProcessorController {

    private final GeolocationService geolocationService;
    private final FileProcessorService fileProcessorService;
    private final ObjectMapper objectMapper;

    public FileProcessorController(GeolocationService geolocationService, FileProcessorService fileProcessorService, ObjectMapper objectMapper) {
        this.geolocationService = geolocationService;
        this.fileProcessorService = fileProcessorService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/files/process")
    public String processFile(HttpServletRequest request, @Validated @RequestBody String content) throws IOException, InvalidIpAddressException, URISyntaxException, IpCheckFailureException {
        String ipAddress = request.getRemoteAddr();
        geolocationService.validateIpAddress(ipAddress);
        List<Output> outputList = fileProcessorService.generateOutput(content);
        return objectMapper.writeValueAsString(outputList);
    }
}
