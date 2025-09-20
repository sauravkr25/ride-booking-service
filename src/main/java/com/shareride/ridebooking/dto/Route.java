package com.shareride.ridebooking.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Route {
    private Long distance;
    private String readableDistance;
    private Long duration;
    private String readableDuration;
    private LocationCoordinate startLocation;
    private LocationCoordinate endLocation;
    private String routePolyline;
    private String travelAdvisory;
    private List<Step> steps;
}
