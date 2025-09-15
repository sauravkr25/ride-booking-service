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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.shareride.ridebooking.exception.ErrorCodes.VEHICLE_NOT_FOUND;
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

        boolean isRideScheduled = rideRepository.existsByDriverIdAndStatus(rideDomain.getDriverId(), RideStatus.SCHEDULED);
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
    public List<RideDomain> getRides(UUID driverId, RideStatus rideStatus) {
        Optional<List<Ride>> ridesFromDB;

        if (Objects.isNull(rideStatus))
            ridesFromDB = rideRepository.findAllByDriverId(driverId);
        else
            ridesFromDB = rideRepository.findAllByDriverIdAndStatus(driverId, rideStatus);

        if (ridesFromDB.isEmpty()) return List.of();

        List<RideDomain> rideDomains = new ArrayList<>();
        for (var r : ridesFromDB.get()) {
            rideDomains.add(RideDomain.from(r));
        }

        return  rideDomains;
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
