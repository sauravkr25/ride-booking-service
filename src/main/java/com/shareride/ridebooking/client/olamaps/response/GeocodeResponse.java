package com.shareride.ridebooking.client.olamaps.response;

import lombok.Data;

import java.util.List;

@Data
public class GeocodeResponse {

    private List<GeocodingResult> geocodingResults;

    private String status;
}
