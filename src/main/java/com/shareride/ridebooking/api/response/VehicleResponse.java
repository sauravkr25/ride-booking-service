package com.shareride.ridebooking.api.response;

import com.shareride.ridebooking.domain.VehicleDomain;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class VehicleResponse {

    private UUID vehicleId;
    private String make;
    private String model;
    private String color;
    private String registrationNumber;

    public static VehicleResponse from(VehicleDomain vehicleDomain) {
        return VehicleResponse.builder()
                .make(vehicleDomain.getMake())
                .model(vehicleDomain.getModel())
                .color(vehicleDomain.getColor())
                .registrationNumber(vehicleDomain.getRegistrationNumber())
                .vehicleId(vehicleDomain.getVehicleId())
                .build();
    }
}
