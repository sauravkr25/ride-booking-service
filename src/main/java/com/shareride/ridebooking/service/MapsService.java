package com.shareride.ridebooking.service;

import com.shareride.ridebooking.dto.DirectionDto;
import com.shareride.ridebooking.dto.GeocodeDto;

public interface MapsService {

    GeocodeDto getGeocode(String address);

    GeocodeDto getReverseGeocode(Double lat, Double lng);

    DirectionDto getDirection(DirectionDto directionDto);
}
