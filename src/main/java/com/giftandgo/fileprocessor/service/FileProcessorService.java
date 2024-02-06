package com.giftandgo.fileprocessor.service;

import com.giftandgo.fileprocessor.model.UserDataMapper;
import com.giftandgo.fileprocessor.model.Output;
import com.giftandgo.fileprocessor.model.UserData;
import com.giftandgo.fileprocessor.utils.UserDataReader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileProcessorService {
    public List<Output> generateOutput(String content) {
        UserDataReader reader = new UserDataReader();
        List<UserData> userDataList = reader.readFileContent(content);
        return userDataList.stream().map(UserDataMapper::mapToJsonOutput).collect(Collectors.toList());
    }
}
