package com.shareride.ridebooking.api.controller;

import com.shareride.ridebooking.api.response.RideCleanupResponse;
import com.shareride.ridebooking.service.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shareride.ridebooking.utils.Constants.Routes.ADMIN;
import static com.shareride.ridebooking.utils.Constants.Routes.API_V1;
import static com.shareride.ridebooking.utils.Constants.Routes.CLEANUP;
import static com.shareride.ridebooking.utils.Constants.Routes.RIDES;
import static com.shareride.ridebooking.utils.Constants.SUCCESS;

@RestController
@RequestMapping(API_V1 + ADMIN + RIDES)
public class AdminController {

    private final RideService rideService;

    public AdminController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping(CLEANUP)
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RideCleanupResponse> triggerRideCleanup() {

        int cancelledCount = rideService.cancelPastScheduledRides();
        RideCleanupResponse response = RideCleanupResponse.builder()
                .status(SUCCESS)
                .message("Ride cleanup process completed successfully.")
                .cancelledRidesCount(cancelledCount)
                .build();

        return ResponseEntity.ok(response);
    }
}
