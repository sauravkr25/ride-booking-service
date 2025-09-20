package com.shareride.ridebooking.api.request;

import com.shareride.ridebooking.domain.VehicleDomain;
import com.shareride.ridebooking.enums.VehicleType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterVehicleRequest {

    @NotBlank(message = "Vehicle make is required")
    @Size(min = 2, max = 50, message = "Make must be between 2 and 50 characters")
    private String make;

    @NotBlank(message = "Vehicle model is required")
    @Size(min = 1, max = 50, message = "Model must be between 1 and 50 characters")
    private String model;

    @NotBlank(message = "Vehicle color is required")
    @Size(min = 2, max = 30, message = "Color must be between 2 and 30 characters")
    private String color;

    @Min(value = 1, message = "Seat capacity must be at least 1")
    @NotNull
    private Integer seatCapacity;

    @NotBlank(message = "Registration number is required")
    @Pattern(regexp = "^[A-Za-z]{2}[ -]{0,1}[0-9]{2}[ -]{0,1}[A-Za-z]{1,2}[ -]{0,1}[0-9]{4}$", message = "Invalid registration number format")
    private String registrationNumber;

    @NotNull(message = "Vehicle type is required")
    private VehicleType vehicleType;

    public VehicleDomain toVehicleDomain() {
        return VehicleDomain.builder()
                .make(this.make)
                .model(this.model)
                .color(this.color)
                .registrationNumber(normalize(this.registrationNumber))
                .seatCapacity(this.seatCapacity)
                .vehicleType(this.vehicleType)
                .build();
    }

    private String normalize(String input) {
        return input.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
    }
}
