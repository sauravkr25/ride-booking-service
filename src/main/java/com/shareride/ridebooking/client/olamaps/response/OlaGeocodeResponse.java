package com.shareride.ridebooking.client.olamaps.response;

import lombok.Data;

import java.util.List;

@Data
public class OlaGeocodeResponse {

    private List<GeocodingResult> geocodingResults;

    private String status;
}
