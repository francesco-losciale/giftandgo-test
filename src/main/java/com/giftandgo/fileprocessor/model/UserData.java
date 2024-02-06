package com.giftandgo.fileprocessor.model;

import java.util.UUID;

import static java.lang.Double.parseDouble;

public record UserData(UUID uuid, String id, String name, String likes, String transport, double avgSpeed,
                       double topSpeed) {
    public static UserData fromString(String content) {
        String[] fields = content.split("\\|");
        return new UserData(UUID.fromString(fields[0]),
                fields[1],
                fields[2],
                fields[3],
                fields[4],
                parseDouble(fields[5]),
                parseDouble(fields[6]));
    }

}
