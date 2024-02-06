package com.giftandgo.fileprocessor.model;

public class UserDataMapper {
    public static Output mapToJsonOutput(UserData userData) {
        return new Output(userData.name(), userData.transport(), userData.topSpeed());
    }
}
