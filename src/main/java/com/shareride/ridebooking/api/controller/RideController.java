package com.shareride.ridebooking.api.controller;

import com.shareride.ridebooking.api.request.CreateRideRequest;
import com.shareride.ridebooking.api.request.UpdateRideRequest;
import com.shareride.ridebooking.api.response.RideResponse;
import com.shareride.ridebooking.domain.RideDomain;
import com.shareride.ridebooking.enums.RideStatus;
import com.shareride.ridebooking.service.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.shareride.ridebooking.utils.Constants.Routes.API_V1;
import static com.shareride.ridebooking.utils.Constants.Routes.CREATE;
import static com.shareride.ridebooking.utils.Constants.Routes.GET;
import static com.shareride.ridebooking.utils.Constants.Routes.RIDES;
import static com.shareride.ridebooking.utils.Constants.Routes.UPDATE_RIDE;

@RestController
@RequestMapping(API_V1 + RIDES)
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping(CREATE)
    public ResponseEntity<RideResponse> createRide(@RequestBody @Valid CreateRideRequest request,
                                                   Authentication authentication) {

        RideDomain rideDomain = request.toRideDomain();
        String driverId = authentication.getName();
        rideDomain.setDriverId(UUID.fromString(driverId));
        rideDomain = rideService.createRide(rideDomain);
        RideResponse response = RideResponse.from(rideDomain);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(GET)
    public ResponseEntity<List<RideResponse>> getRides(@RequestParam(required = false) RideStatus status,
                                                       @RequestParam(required = false) boolean includeHistory,
                                                       Authentication authentication) {

        String driverId = authentication.getName();
        List<RideDomain> rideDomains = rideService.getRides(UUID.fromString(driverId), status, includeHistory);
        List<RideResponse> response = rideDomains.stream()
                .map(RideResponse::from)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping(UPDATE_RIDE)
    public ResponseEntity<RideResponse> updateRide(@PathVariable UUID rideId,
                                                   @RequestBody @Valid UpdateRideRequest request,
                                                   Authentication authentication) {

        RideDomain rideDomain = request.toRideDomain();
        String driverId = authentication.getName();
        rideDomain.setDriverId(UUID.fromString(driverId));
        rideDomain.setRideId(rideId);
        rideDomain = rideService.updateRide(rideDomain);
        RideResponse response = RideResponse.from(rideDomain);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
