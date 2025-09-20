package com.shareride.ridebooking.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GeocodeDto {

    private Integer numberOfResults;
    private List<PlaceDetails> results;

}
