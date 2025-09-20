package com.shareride.ridebooking.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LocationCoordinate {

    private Double latitude;
    private Double longitude;
}
