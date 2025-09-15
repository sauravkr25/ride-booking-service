package com.shareride.ridebooking.client.olamaps.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReverseGeocodeResponse {

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("info_messages")
    private List<Object> infoMessages;

    @JsonProperty("results")
    private List<GeocodingResult> results;

    @JsonProperty("plus_code")
    private PlusCode plusCode;

    private String status;

}
