package com.shareride.ridebooking.api.controller;

import com.shareride.ridebooking.client.olamaps.impl.OlaMapsClient;
import com.shareride.ridebooking.client.olamaps.response.GeocodeResponse;
import com.shareride.ridebooking.client.olamaps.response.ReverseGeocodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.shareride.ridebooking.utils.Constants.Routes.API_V1;
import static com.shareride.ridebooking.utils.Constants.Routes.PLACES_GEOCODE;
import static com.shareride.ridebooking.utils.Constants.Routes.MAPS;
import static com.shareride.ridebooking.utils.Constants.Routes.PLACES_REVERSE_GEOCODE;

@RestController
@RequestMapping(API_V1 + MAPS)
public class MapsController {

    private final OlaMapsClient olaMapsClient;

    public MapsController(OlaMapsClient olaMapsClient) {
        this.olaMapsClient = olaMapsClient;
    }


    @GetMapping(PLACES_GEOCODE)
    public GeocodeResponse getGeocode(@RequestParam String address) {
        return olaMapsClient.getGeocode(address);
    }

    @GetMapping(PLACES_REVERSE_GEOCODE)
    public ReverseGeocodeResponse getReverseGeocode(@RequestParam Double lat, @RequestParam Double lng) {
        return olaMapsClient.getReverseGeocode(lat, lng);
    }


}
