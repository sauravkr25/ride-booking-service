package com.shareride.ridebooking.api.controller;

import com.shareride.ridebooking.api.response.DirectionResponse;
import com.shareride.ridebooking.api.response.GeocodeResponse;
import com.shareride.ridebooking.client.olamaps.IOlaMapsClient;
import com.shareride.ridebooking.dto.DirectionDto;
import com.shareride.ridebooking.dto.GeocodeDto;
import com.shareride.ridebooking.enums.TravelMode;
import com.shareride.ridebooking.service.MapsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.shareride.ridebooking.utils.Constants.Routes.API_V1;
import static com.shareride.ridebooking.utils.Constants.Routes.DIRECTIONS;
import static com.shareride.ridebooking.utils.Constants.Routes.GEOCODE;
import static com.shareride.ridebooking.utils.Constants.Routes.PLACES;
import static com.shareride.ridebooking.utils.Constants.Routes.MAPS;
import static com.shareride.ridebooking.utils.Constants.Routes.REVERSE_GEOCODE;
import static com.shareride.ridebooking.utils.Constants.Routes.ROUTING;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_V1 + MAPS)
public class MapsController {

    private final IOlaMapsClient mapsClient;
    private final MapsService mapsService;

    @GetMapping(PLACES + GEOCODE)
    public ResponseEntity<GeocodeResponse> getGeocode(@RequestParam String address) {

        GeocodeDto geocodeDto = mapsService.getGeocode(address);
        GeocodeResponse response = GeocodeResponse
                .builder()
                .numberOfResults(geocodeDto.getNumberOfResults())
                .results(geocodeDto.getResults())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping(PLACES + REVERSE_GEOCODE)
    public ResponseEntity<GeocodeResponse> getReverseGeocode(@RequestParam Double lat, @RequestParam Double lng) {

        GeocodeDto geocodeDto = mapsService.getReverseGeocode(lat, lng);
        GeocodeResponse response = GeocodeResponse
                .builder()
                .numberOfResults(geocodeDto.getNumberOfResults())
                .results(geocodeDto.getResults())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping(ROUTING + DIRECTIONS)
    public ResponseEntity<DirectionResponse> getDirection(@RequestParam Double originLat, @RequestParam Double originLng,
                                          @RequestParam Double destLat, @RequestParam Double destLng,
                                          @RequestParam(required = false, defaultValue = "DRIVING") TravelMode travelMode,
                                          @RequestParam(required = false, defaultValue = "false")Boolean includeTrafficMetadata,
                                          @RequestParam(required = false, defaultValue = "false")Boolean includeSteps,
                                          @RequestParam(required = false, defaultValue = "false")Boolean includeAlternatives) {

        DirectionDto directionDto = DirectionDto.builder()
                .originLat(originLat)
                .originLng(originLng)
                .destLat(destLat)
                .destLng(destLng)
                .mode(travelMode)
                .trafficMetadata(includeTrafficMetadata)
                .steps(includeSteps)
                .alternatives(includeAlternatives)
                .build();

        directionDto = mapsService.getDirection(directionDto);
        DirectionResponse response = DirectionResponse.builder()
                .routes(directionDto.getRoutes())
                .numberOfRoutes(directionDto.getNumberOfRoutes())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
