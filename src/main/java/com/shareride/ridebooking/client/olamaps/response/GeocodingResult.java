package com.shareride.ridebooking.client.olamaps.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GeocodingResult {

    @JsonProperty("formatted_address")
    private String formattedAddress;

    @JsonProperty("types")
    private List<String> types;

    @JsonProperty("name")
    private String name;

    @JsonProperty("geometry")
    private Geometry geometry;

    @JsonProperty("address_components")
    private List<AddressComponent> addressComponents;

    @JsonProperty("plus_code")
    private PlusCode plusCode;

    @JsonProperty("place_id")
    private String placeId;

    @JsonProperty("layer")
    private List<String> layer;
}
