package com.shareride.ridebooking.repository;

import com.shareride.ridebooking.enums.RideStatus;
import com.shareride.ridebooking.repository.entity.Ride;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(
            value = "SELECT * FROM rides r " +
                    "WHERE r.status = 'SCHEDULED' " +
                    "AND r.available_seats > 0 " +
                    "AND r.departure_time > NOW()" +
                    "AND r.driver_id != :riderId " +
                    "AND r.departure_time BETWEEN :startTime AND :endTime " +
                    "AND ST_DWithin(r.origin, ST_MakePoint(:originLongitude, :originLatitude)::geography, :originRadiusMeters) " +
                    "AND ST_DWithin(r.destination, ST_MakePoint(:destLongitude, :destLatitude)::geography, :destRadiusMeters) " +
                    "LIMIT 50",
            nativeQuery = true
    )
    List<Ride> findCandidateRides(
            @Param("originLongitude") double originLongitude,
            @Param("originLatitude") double originLatitude,
            @Param("originRadiusMeters") long originRadiusMeters,
            @Param("destLongitude") double destLongitude,
            @Param("destLatitude") double destLatitude,
            @Param("destRadiusMeters") long destRadiusMeters,
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime,
            @Param("riderId") UUID riderId
    );

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Ride r " +
            "SET r.status = 'CANCELLED' " +
            "WHERE r.status = 'SCHEDULED' AND r.departureTime < :now")
    int cancelPastScheduledRides(@Param("now") Instant now);
}
