package com.shareride.ridebooking.service;

import com.shareride.ridebooking.domain.VehicleDomain;

import java.util.List;
import java.util.UUID;

public interface VehicleService {

    VehicleDomain registerVehicle (VehicleDomain vehicleDomain);

    List<VehicleDomain> getVehicles (UUID ownerId);

}
