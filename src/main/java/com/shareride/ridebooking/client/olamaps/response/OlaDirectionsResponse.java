package com.shareride.ridebooking.client.olamaps.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OlaDirectionsResponse {

    private String status;

    @JsonProperty("geocoded_waypoints")
    private List<GeocodedWaypoint> geocodedWaypoints;

    private List<Route> routes;

    @JsonProperty("source_from")
    private String sourceFrom;
}
