package com.shareride.ridebooking.client.olamaps.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Step {

    private String instructions;
    private Long distance;

    @JsonProperty("readable_distance")
    private String readableDistance;

    private String maneuver;
    private Long duration;

    @JsonProperty("readable_duration")
    private String readableDuration;

    @JsonProperty("start_location")
    private Coordinate startLocation;

    @JsonProperty("end_location")
    private Coordinate endLocation;

    @JsonProperty("bearing_before")
    private Long bearingBefore;

    @JsonProperty("bearing_after")
    private Long bearingAfter;

}
