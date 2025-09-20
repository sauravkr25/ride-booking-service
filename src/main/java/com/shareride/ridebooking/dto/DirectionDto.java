package com.shareride.ridebooking.dto;

import com.shareride.ridebooking.enums.TravelMode;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DirectionDto {

    Double originLat;
    Double originLng;
    Double destLat;
    Double destLng;
    TravelMode mode;
    Boolean trafficMetadata;
    Boolean steps;
    Boolean alternatives;
    private Integer numberOfRoutes;
    private List<Route> routes;
}
