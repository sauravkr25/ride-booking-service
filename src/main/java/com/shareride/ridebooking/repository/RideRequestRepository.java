package com.shareride.ridebooking.repository;

import com.shareride.ridebooking.enums.RequestStatus;
import com.shareride.ridebooking.repository.entity.Ride;
import com.shareride.ridebooking.repository.entity.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest, UUID> {
    boolean existsByRideAndRiderId(Ride ride, UUID riderId);

    List<RideRequest> findByRideAndStatus(Ride ride, RequestStatus status);

    List<RideRequest> findByRide(Ride ride);
}
