package com.shareride.ridebooking.service.impl;

import com.shareride.ridebooking.client.olamaps.IOlaMapsClient;
import com.shareride.ridebooking.client.olamaps.response.OlaDirectionsResponse;
import com.shareride.ridebooking.client.olamaps.response.OlaGeocodeResponse;
import com.shareride.ridebooking.client.olamaps.response.OlaReverseGeocodeResponse;
import com.shareride.ridebooking.dto.DirectionDto;
import com.shareride.ridebooking.dto.GeocodeDto;
import com.shareride.ridebooking.service.MapsService;
import com.shareride.ridebooking.utils.OlaDirectionsResponseMapper;
import com.shareride.ridebooking.utils.OlaGeocodeResponseMapper;
import com.shareride.ridebooking.utils.OlaReverseGeocodeResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapsServiceImpl implements MapsService {

    private final IOlaMapsClient olaMapsClient;

    @Override
    public GeocodeDto getGeocode(String address) {

        OlaGeocodeResponse olaGeocodeResponse = olaMapsClient.getGeocode(address);
        return OlaGeocodeResponseMapper.toGeocodeDto(olaGeocodeResponse);
    }

    @Override
    public GeocodeDto getReverseGeocode(Double lat, Double lng) {

        OlaReverseGeocodeResponse olaReverseGeocodeResponse = olaMapsClient.getReverseGeocode(lat, lng);
        return OlaReverseGeocodeResponseMapper.toGeocodeDto(olaReverseGeocodeResponse);
    }

    @Override
    public DirectionDto getDirection(DirectionDto directionDto) {

        OlaDirectionsResponse response = callClient(directionDto);
        return OlaDirectionsResponseMapper.toDirectionDto(response);
    }

    private OlaDirectionsResponse callClient(DirectionDto directionDto) {
        return olaMapsClient.getDirections(
                directionDto.getOriginLat(), directionDto.getOriginLng(),
                directionDto.getDestLat(), directionDto.getDestLng(),
                directionDto.getMode().name().toLowerCase(), directionDto.getTrafficMetadata(),
                directionDto.getSteps(), directionDto.getAlternatives()
        );
    }
}
