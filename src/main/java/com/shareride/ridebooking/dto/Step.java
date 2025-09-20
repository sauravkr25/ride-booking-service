package com.shareride.ridebooking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Step {
    private String instructions;
    private String maneuver;
    private Long distance;
    private String readableDistance;
    private Long duration;
    private String readableDuration;
    private LocationCoordinate startLocation;
    private LocationCoordinate endLocation;
}
