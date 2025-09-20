package com.shareride.ridebooking.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class SearchRideDto {

    private LocationCoordinate origin;
    private LocationCoordinate destination;
    private Instant time;
    private String routePolyline;
    private UUID riderId;
}
