package com.shareride.ridebooking.client.olamaps.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Route {

    private String summary;
    private List<Leg> legs;

    @JsonProperty("overview_polyline")
    private String overviewPolyline;

    @JsonProperty("travel_advisory")
    private String travelAdvisory;

    private String copyrights;
    private List<String> warnings;
}
