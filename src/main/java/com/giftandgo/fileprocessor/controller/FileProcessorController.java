package com.giftandgo.fileprocessor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giftandgo.fileprocessor.model.Output;
import com.giftandgo.fileprocessor.service.FileProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FileProcessorController {

    private final FileProcessorService fileProcessorService;
    private final ObjectMapper objectMapper;

    public FileProcessorController(FileProcessorService fileProcessorService, ObjectMapper objectMapper) {
        this.fileProcessorService = fileProcessorService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/files/process")
    public String processFile(@RequestBody String content) throws JsonProcessingException {
        List<Output> outputList = fileProcessorService.generateOutput(content);
        return objectMapper.writeValueAsString(outputList);
    }
}
