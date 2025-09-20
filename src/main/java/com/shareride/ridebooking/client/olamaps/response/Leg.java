package com.shareride.ridebooking.client.olamaps.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Leg {

    private List<Step> steps;
    private Long distance;

    @JsonProperty("readable_distance")
    private String readableDistance;

    private Long duration;

    @JsonProperty("readable_duration")
    private String readableDuration;

    @JsonProperty("start_location")
    private Coordinate startLocation;

    @JsonProperty("end_location")
    private Coordinate endLocation;

    @JsonProperty("start_address")
    private String startAddress;

    @JsonProperty("end_address")
    private String endAddress;
}
