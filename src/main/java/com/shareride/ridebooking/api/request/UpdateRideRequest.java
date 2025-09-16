package com.shareride.ridebooking.api.request;

import com.shareride.ridebooking.domain.RideDomain;
import com.shareride.ridebooking.enums.RideStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UpdateRideRequest {

    @Future(message = "New departure time must be in the future")
    private Instant departureTime;

    @Min(value = 1, message = "Available seats must be at least 1")
    private Integer availableSeats;

    private RideStatus status;

    public RideDomain toRideDomain() {
        return RideDomain.builder()
                .departureTime(this.departureTime)
                .availableSeats(this.availableSeats)
                .rideStatus(this.status)
                .build();
    }
}
