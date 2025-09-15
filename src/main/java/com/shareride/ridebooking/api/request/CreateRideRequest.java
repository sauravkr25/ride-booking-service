package com.shareride.ridebooking.api.request;

import com.shareride.ridebooking.domain.RideDomain;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class CreateRideRequest {

    @NotNull(message = "Origin location is required")
    private LocationCoordinateRequest origin;

    @NotNull(message = "Destination location is required")
    private LocationCoordinateRequest destination;

    @NotNull(message = "Departure time is required")
    @FutureOrPresent(message = "Departure time must be in the future")
    private Instant departureTime;

    @Min(value = 1, message = "Available seats must be at least 1")
    private int availableSeats;

    @NotNull
    private UUID vehicleId;

//    @NotBlank(message = "Route polyline is required")
    private String routePolyline;

    public RideDomain toRideDomain() {
        return RideDomain.builder()
                .origin(this.origin.toLocationCoordinate())
                .destination(this.destination.toLocationCoordinate())
                .departureTime(this.departureTime)
                .availableSeats(this.availableSeats)
                .routePolyline(this.routePolyline)
                .vehicleId(this.vehicleId)
                .build();
    }
}