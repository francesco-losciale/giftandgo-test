package com.giftandgo.fileprocessor.utils;

import com.giftandgo.fileprocessor.model.UserData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserDataReader {

    public List<UserData> readFileContent(String fileContent) {
        return Arrays.stream(fileContent.split("\n"))
                .map(UserData::fromString)
                .collect(Collectors.toList());
    }
}
