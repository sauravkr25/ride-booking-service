package com.shareride.ridebooking.repository;

import com.shareride.ridebooking.repository.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    Optional<List<Vehicle>> findAllByOwnerId(UUID ownerId);
}
