package com.shareride.ridebooking.service;

import com.shareride.ridebooking.domain.RideRequestDomain;
import com.shareride.ridebooking.enums.RequestStatus;

import java.util.List;
import java.util.UUID;

public interface RideRequestService {

    RideRequestDomain createRideRequest(RideRequestDomain rideRequestDomain);

    List<RideRequestDomain> getRideRequests(UUID rideId, UUID driverId, RequestStatus requestStatus);

    RideRequestDomain updateRideRequest(RideRequestDomain rideRequestDomain);
}
