package com.shareride.ridebooking.api.response;

import com.shareride.ridebooking.dto.LocationCoordinate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationCoordinateResponse {
    private Double latitude;
    private Double longitude;

    public static LocationCoordinateResponse from(LocationCoordinate locationCoordinate) {
        return LocationCoordinateResponse.builder()
                .latitude(locationCoordinate.getLatitude())
                .longitude(locationCoordinate.getLongitude())
                .build();
    }
}
