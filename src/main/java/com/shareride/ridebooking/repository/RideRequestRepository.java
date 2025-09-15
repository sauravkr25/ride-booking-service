package com.shareride.ridebooking.repository;

import com.shareride.ridebooking.repository.entity.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest, UUID> {
}
