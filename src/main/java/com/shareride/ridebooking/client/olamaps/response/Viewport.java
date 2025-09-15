package com.shareride.ridebooking.client.olamaps.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Viewport {
    @JsonProperty("southwest")
    private Coordinate southwest;

    @JsonProperty("northeast")
    private Coordinate northeast;
}
