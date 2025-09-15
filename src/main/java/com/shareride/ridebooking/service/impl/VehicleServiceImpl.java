package com.shareride.ridebooking.service.impl;

import com.shareride.ridebooking.domain.VehicleDomain;
import com.shareride.ridebooking.repository.VehicleRepository;
import com.shareride.ridebooking.repository.entity.Vehicle;
import com.shareride.ridebooking.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    public VehicleDomain registerVehicle(VehicleDomain vehicleDomain) {
        Vehicle vehicleToSave = createVehicleEntity(vehicleDomain);
        Vehicle savedVehicle = vehicleRepository.save(vehicleToSave);
        vehicleDomain.updateFrom(savedVehicle);
        return vehicleDomain;
    }

    @Override
    public List<VehicleDomain> getVehicles(UUID ownerId) {
        Optional<List<Vehicle>> vehiclesFromDB = vehicleRepository.findAllByOwnerId(ownerId);

        if (vehiclesFromDB.isEmpty()) {
            return  List.of();
        }

        List<VehicleDomain> domains = new ArrayList<>();
        for (var v : vehiclesFromDB.get()) {
            domains.add(VehicleDomain.from(v));
        }
        return domains;
    }

    private Vehicle createVehicleEntity(VehicleDomain vehicleDomain) {
        return Vehicle.builder()
                .make(vehicleDomain.getMake())
                .model(vehicleDomain.getModel())
                .color(vehicleDomain.getColor())
                .registrationNumber(vehicleDomain.getRegistrationNumber())
                .ownerId(vehicleDomain.getOwnerId())
                .build();
    }
}
