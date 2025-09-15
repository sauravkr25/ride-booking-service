package com.shareride.ridebooking.domain;

import com.shareride.ridebooking.api.request.LocationCoordinateRequest;
import com.shareride.ridebooking.dto.LocationCoordinate;
import com.shareride.ridebooking.enums.RideStatus;
import com.shareride.ridebooking.repository.entity.Ride;
import com.shareride.ridebooking.utils.JtsUtil;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class RideDomain {

    private LocationCoordinate origin;
    private LocationCoordinate destination;
    private Instant departureTime;
    private int availableSeats;
    private String routePolyline;
    private UUID driverId;
    private RideStatus rideStatus;
    private UUID vehicleId;
    private VehicleDomain vehicleDomain;

    public void updateFrom(Ride savedRide) {
        this.setRideStatus(savedRide.getStatus());
    }

    public static RideDomain from(Ride ride) {
        return RideDomain.builder()
                .origin(JtsUtil.convertToLocationCoordinate(ride.getOrigin()))
                .destination(JtsUtil.convertToLocationCoordinate(ride.getDestination()))
                .departureTime(ride.getDepartureTime())
                .availableSeats(ride.getAvailableSeats())
                .routePolyline(ride.getRoutePolyline())
                .driverId(ride.getDriverId())
                .rideStatus(ride.getStatus())
                .vehicleDomain(VehicleDomain.from(ride.getVehicle()))
                .build();
    }
}
