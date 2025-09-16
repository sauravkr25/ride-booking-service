package com.shareride.ridebooking.service;

import com.shareride.ridebooking.domain.RideDomain;
import com.shareride.ridebooking.enums.RideStatus;

import java.util.List;
import java.util.UUID;

public interface RideService {

    RideDomain createRide(RideDomain rideDomain);

    List<RideDomain> getRides (UUID driverId, RideStatus rideStatus, boolean includeHistory);

    RideDomain updateRide(RideDomain rideDomain);
}
