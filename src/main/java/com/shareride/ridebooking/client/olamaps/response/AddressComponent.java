package com.shareride.ridebooking.client.olamaps.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AddressComponent {

    @JsonProperty("types")
    private List<String> types;

    @JsonProperty("short_name")
    private String shortName;

    @JsonProperty("long_name")
    private String longName;
}
