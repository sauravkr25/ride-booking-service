package com.shareride.ridebooking.api.response;

import com.shareride.ridebooking.domain.RideDomain;
import com.shareride.ridebooking.enums.RideStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
public class RideResponse {

    private UUID rideId;
    private LocationCoordinateResponse origin;
    private LocationCoordinateResponse destination;
    private Instant departureTime;
    private int availableSeats;
    private String routePolyline;
    private RideStatus status;
    private VehicleResponse vehicle;

    public static RideResponse from(RideDomain rideDomain) {
        return RideResponse.builder()
                .rideId(rideDomain.getRideId())
                .origin(LocationCoordinateResponse.from(rideDomain.getOrigin()))
                .destination(LocationCoordinateResponse.from(rideDomain.getDestination()))
                .departureTime(rideDomain.getDepartureTime())
                .availableSeats(rideDomain.getAvailableSeats())
                .routePolyline(rideDomain.getRoutePolyline())
                .status(rideDomain.getRideStatus())
                .vehicle(VehicleResponse.from(rideDomain.getVehicleDomain()))
                .build();
    }
}
