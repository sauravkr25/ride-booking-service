package com.shareride.ridebooking.repository;

import com.shareride.ridebooking.enums.RideStatus;
import com.shareride.ridebooking.repository.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RideRepository extends JpaRepository<Ride, UUID> {

    boolean existsByDriverIdAndStatus(UUID driverId, RideStatus status);

    boolean existsByDriverIdAndStatusAndDepartureTimeAfter(UUID driverId, RideStatus status, Instant now);

    Optional<List<Ride>> findAllByDriverId(UUID driverId);

    Optional<List<Ride>> findAllByDriverIdAndStatus(UUID driverId, RideStatus status);

    Optional<List<Ride>> findAllByDriverIdAndDepartureTimeAfter(UUID driverId, Instant now);

    Optional<List<Ride>> findAllByDriverIdAndStatusAndDepartureTimeAfter(UUID driverId, RideStatus status, Instant now);

    Optional<List<Ride>> findAllByDepartureTimeAfter(Instant now);
}
