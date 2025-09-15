package com.shareride.ridebooking.api.controller;

import com.shareride.ridebooking.api.request.RegisterVehicleRequest;
import com.shareride.ridebooking.api.response.VehicleResponse;
import com.shareride.ridebooking.domain.VehicleDomain;
import com.shareride.ridebooking.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.shareride.ridebooking.utils.Constants.Routes.API_V1;
import static com.shareride.ridebooking.utils.Constants.Routes.GET;
import static com.shareride.ridebooking.utils.Constants.Routes.REGISTER;
import static com.shareride.ridebooking.utils.Constants.Routes.VEHICLES;

@RestController
@RequestMapping(API_V1 + VEHICLES)
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping(REGISTER)
    public ResponseEntity<VehicleResponse> registerVehicle (@RequestBody @Valid RegisterVehicleRequest request,
                                                            Authentication authentication) {

        VehicleDomain vehicleDomain = request.toVehicleDomain();
        vehicleDomain.setOwnerId(UUID.fromString(authentication.getName()));
        vehicleDomain = vehicleService.registerVehicle(vehicleDomain);
        VehicleResponse response = VehicleResponse.from(vehicleDomain);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping(GET)
    public ResponseEntity<List<VehicleResponse>> getVehicles (Authentication authentication) {

        String VehicleOwnerId = authentication.getName();
        List<VehicleDomain> vehicleDomains = vehicleService.getVehicles(UUID.fromString(VehicleOwnerId));
        List<VehicleResponse> response = new ArrayList<>();
        for (var v : vehicleDomains) {
            response.add(VehicleResponse.from(v));
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
