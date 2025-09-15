package com.shareride.ridebooking.client.olamaps.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Geometry {
    @JsonProperty("viewport")
    private Viewport viewport;

    @JsonProperty("location")
    private Coordinate location;

    @JsonProperty("location_type")
    private String locationType;
}
