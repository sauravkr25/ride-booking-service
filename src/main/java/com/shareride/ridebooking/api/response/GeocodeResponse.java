package com.shareride.ridebooking.api.response;

import com.shareride.ridebooking.dto.PlaceDetails;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GeocodeResponse {

    private Integer numberOfResults;
    private List<PlaceDetails> results;
}
