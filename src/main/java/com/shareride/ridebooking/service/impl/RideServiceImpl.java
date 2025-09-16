package com.shareride.ridebooking.service.impl;

import com.shareride.ridebooking.domain.RideDomain;
import com.shareride.ridebooking.domain.VehicleDomain;
import com.shareride.ridebooking.enums.RideStatus;
import com.shareride.ridebooking.exception.ApplicationException;
import com.shareride.ridebooking.exception.ErrorCodes;
import com.shareride.ridebooking.repository.RideRepository;
import com.shareride.ridebooking.repository.VehicleRepository;
import com.shareride.ridebooking.repository.entity.Ride;
import com.shareride.ridebooking.repository.entity.Vehicle;
import com.shareride.ridebooking.service.RideService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.shareride.ridebooking.exception.ErrorCodes.VEHICLE_NOT_FOUND;
import static com.shareride.ridebooking.utils.Constants.CAUSE;
import static com.shareride.ridebooking.utils.JtsUtil.convertToPoint;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public RideDomain createRide(RideDomain rideDomain) {
        Vehicle vehicleFromDB = vehicleRepository.findById(rideDomain.getVehicleId())
                .orElseThrow(() -> ApplicationException.of(VEHICLE_NOT_FOUND));

        boolean isRideScheduled = rideRepository.existsByDriverIdAndStatusAndDepartureTimeAfter(rideDomain.getDriverId(), RideStatus.SCHEDULED, Instant.now());
        if (isRideScheduled) {
            throw ApplicationException.of(ErrorCodes.DRIVER_RIDE_CONFLICT);
        }

        Ride rideToSave = createRideEntity(rideDomain, vehicleFromDB);
        rideToSave.setStatus(RideStatus.SCHEDULED);
        Ride savedRide = rideRepository.save(rideToSave);
        rideDomain.updateFrom(savedRide);
        rideDomain.setVehicleDomain(VehicleDomain.from(vehicleFromDB));
        return rideDomain;
    }

    @Override
    public List<RideDomain> getRides(UUID driverId, RideStatus rideStatus,  boolean includeHistory) {
        Optional<List<Ride>> ridesFromDB;
        if (includeHistory)
            ridesFromDB = getAllRides(driverId, rideStatus);
        else
            ridesFromDB = getFutureRides(driverId, rideStatus);

        if (ridesFromDB.isEmpty()) return List.of();

        List<RideDomain> rideDomains = new ArrayList<>();
        for (var r : ridesFromDB.get()) {
            rideDomains.add(RideDomain.from(r));
        }
        return rideDomains;
    }

    @Override
    @Transactional
    public RideDomain updateRide(RideDomain rideDomain) {
        Ride ride = rideRepository.findById(rideDomain.getRideId())
                .orElseThrow(() -> ApplicationException.of(ErrorCodes.RIDE_NOT_FOUND));

        if (!ride.getDriverId().equals(rideDomain.getDriverId())) {
            throw ApplicationException.of(ErrorCodes.FORBIDDEN, Map.of(CAUSE, "User is not the owner of this ride."));
        }
        if (ride.getStatus() != RideStatus.SCHEDULED) {
            throw ApplicationException.of(ErrorCodes.RIDE_UPDATE_NOT_ALLOWED);
        }

        boolean isUpdated = false;
        if (rideDomain.getDepartureTime() != null) {
            ride.setDepartureTime(rideDomain.getDepartureTime());
            isUpdated = true;
        }
        if (rideDomain.getAvailableSeats() != null) {
            ride.setAvailableSeats(rideDomain.getAvailableSeats());
            isUpdated = true;
        }
        if (rideDomain.getRideStatus() != null) {
            // Only allow updating to CANCELLED state via this method.
            if (rideDomain.getRideStatus() == RideStatus.CANCELLED) {
                ride.setStatus(rideDomain.getRideStatus());
                isUpdated = true;
            } else {
                throw ApplicationException.of(ErrorCodes.INVALID_STATUS_TRANSITION, Map.of(CAUSE, "Status can only be updated to CANCELLED."));
            }
        }

        if (isUpdated) {
            ride = rideRepository.save(ride);
        }
        return RideDomain.from(ride);
    }

    private Optional<List<Ride>> getAllRides(UUID driverId, RideStatus rideStatus) {
        Optional<List<Ride>> ridesFromDB;
        if (Objects.isNull(rideStatus))
            ridesFromDB = rideRepository.findAllByDriverId(driverId);
        else
            ridesFromDB = rideRepository.findAllByDriverIdAndStatus(driverId, rideStatus);
        return  ridesFromDB;
    }

    private Optional<List<Ride>> getFutureRides(UUID driverId, RideStatus rideStatus) {
        Optional<List<Ride>> ridesFromDB;
        if (Objects.isNull(rideStatus))
            ridesFromDB = rideRepository.findAllByDriverIdAndDepartureTimeAfter(driverId, Instant.now());
        else
            ridesFromDB = rideRepository.findAllByDriverIdAndStatusAndDepartureTimeAfter(driverId, rideStatus, Instant.now());
        return  ridesFromDB;
    }


    private Ride createRideEntity(RideDomain rideDomain, Vehicle vehicleFromDB) {
        return Ride.builder()
                .driverId(rideDomain.getDriverId())
                .departureTime(rideDomain.getDepartureTime())
                .availableSeats(rideDomain.getAvailableSeats())
                .routePolyline(rideDomain.getRoutePolyline())
                .origin(convertToPoint(rideDomain.getOrigin()))
                .destination(convertToPoint(rideDomain.getDestination()))
                .vehicle(vehicleFromDB)
                .build();
    }
}
