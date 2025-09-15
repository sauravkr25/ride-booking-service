package com.shareride.ridebooking.domain;

import com.shareride.ridebooking.repository.entity.Vehicle;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class VehicleDomain {

    private String make;
    private String model;
    private String color;
    private String registrationNumber;
    private UUID ownerId;
    private UUID vehicleId;

    public void updateFrom(Vehicle savedVehicle) {
        this.setVehicleId(savedVehicle.getId());
    }

    public static VehicleDomain from(Vehicle vehicle) {
        return VehicleDomain.builder()
                .vehicleId(vehicle.getId())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .color(vehicle.getColor())
                .registrationNumber(vehicle.getRegistrationNumber())
                .ownerId(vehicle.getOwnerId())
                .build();
    }
}
