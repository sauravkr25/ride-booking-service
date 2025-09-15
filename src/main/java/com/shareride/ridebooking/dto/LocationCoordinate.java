package com.shareride.ridebooking.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationCoordinate {

    private Double latitude;
    private Double longitude;
}
