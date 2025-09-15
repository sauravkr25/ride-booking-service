package com.shareride.ridebooking.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shareride.ridebooking.dto.LocationCoordinate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationCoordinateRequest {

    @JsonProperty("lat")
    @NotNull(message = "Latitude is required")
    private Double latitude;

    @JsonProperty("lng")
    @NotNull(message = "Longitude is required")
    private Double longitude;

    public LocationCoordinate toLocationCoordinate() {
        return LocationCoordinate.builder()
                .latitude(this.latitude)
                .longitude(this.longitude)
                .build();
    }
}
