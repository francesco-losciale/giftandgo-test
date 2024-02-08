package com.giftandgo.geolocation.service;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "request_sent")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RequestSent {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;
    private String uri;
    private LocalDateTime createdAt;
    private int httpStatusCode;
    private String ipAddress;
    private String countryCode;
    private String ipProvider;
    private long responseTime;
}
