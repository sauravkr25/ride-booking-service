package com.shareride.ridebooking.api.controller;

import com.shareride.ridebooking.api.response.RideRequestResponse;
import com.shareride.ridebooking.domain.RideRequestDomain;
import com.shareride.ridebooking.enums.RequestStatus;
import com.shareride.ridebooking.service.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.shareride.ridebooking.utils.Constants.Routes.API_V1;
import static com.shareride.ridebooking.utils.Constants.Routes.RIDE_REQUEST;
import static com.shareride.ridebooking.utils.Constants.Routes.UPDATE_RIDE_REQUEST;

@RestController
@RequestMapping(API_V1)
@RequiredArgsConstructor
public class RideRequestController {

    private final RideRequestService rideRequestService;

    @PostMapping(RIDE_REQUEST)
    public ResponseEntity<RideRequestResponse> createRideRequest(@PathVariable UUID rideId,
                                                                 Authentication authentication) {
        UUID riderId = UUID.fromString(authentication.getName());
        RideRequestDomain rideRequestDomain = RideRequestDomain
                .builder()
                .rideId(rideId)
                .riderId(riderId)
                .build();
        rideRequestDomain = rideRequestService.createRideRequest(rideRequestDomain);
        RideRequestResponse response = RideRequestResponse.from(rideRequestDomain);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping(RIDE_REQUEST)
    public ResponseEntity<List<RideRequestResponse>> getRideRequests(@PathVariable UUID rideId,
                                                                 @RequestParam(required = false) RequestStatus status,
                                                                 Authentication authentication) {
        UUID driverId = UUID.fromString(authentication.getName());
        List<RideRequestDomain> domains = rideRequestService.getRideRequests(rideId, driverId, status);
        List<RideRequestResponse> response = domains.stream()
                .map(domain -> RideRequestResponse.from(domain))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping(UPDATE_RIDE_REQUEST)
    public ResponseEntity<RideRequestResponse> updateRideRequest(@PathVariable UUID rideId,
                                                                 @PathVariable UUID requestId,
                                                                 @RequestParam RequestStatus status,
                                                                 Authentication authentication){
        RideRequestDomain domain = RideRequestDomain
                .builder()
                .rideId(rideId)
                .requestId(requestId)
                .status(status)
                .userId(UUID.fromString(authentication.getName()))
                .build();

        domain = rideRequestService.updateRideRequest(domain);
        RideRequestResponse response = RideRequestResponse.from(domain);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
