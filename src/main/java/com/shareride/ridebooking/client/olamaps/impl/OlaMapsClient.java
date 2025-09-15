package com.shareride.ridebooking.client.olamaps.impl;

import com.shareride.ridebooking.client.olamaps.IOlaMapsClient;
import com.shareride.ridebooking.client.olamaps.auth.OlaMapsAuthClient;
import com.shareride.ridebooking.client.olamaps.response.GeocodeResponse;
import com.shareride.ridebooking.client.olamaps.response.ReverseGeocodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static com.shareride.ridebooking.utils.Constants.AUTHORIZATION;
import static com.shareride.ridebooking.utils.Constants.BEARER_;
import static com.shareride.ridebooking.utils.Constants.Properties.OLAMAPS_API_BASE_URL;

@Component
public class OlaMapsClient implements IOlaMapsClient {

    private final OlaMapsApi olaMapsApi;
    private final OlaMapsAuthClient olaMapsAuthClient;

    public OlaMapsClient(OlaMapsApi olaMapsApi, OlaMapsAuthClient olaMapsAuthClient) {
        this.olaMapsApi = olaMapsApi;
        this.olaMapsAuthClient = olaMapsAuthClient;
    }

    @Override
    public GeocodeResponse getGeocode(String address) {
        return olaMapsApi.getGeocode( BEARER_ + olaMapsAuthClient.getTokenResponse().getAccessToken(), address);
    }

    @Override
    public ReverseGeocodeResponse getReverseGeocode(Double lat, Double lng) {
        return olaMapsApi.getReverseGeocode(BEARER_ + olaMapsAuthClient.getTokenResponse().getAccessToken(), lat + "," + lng);
    }


    @FeignClient(name = "olaMapsApi", url = OLAMAPS_API_BASE_URL)
    public interface OlaMapsApi {

        @GetMapping(value = "/places/v1/geocode")
        GeocodeResponse getGeocode(@RequestHeader(AUTHORIZATION) String token, @RequestParam String address);

        @GetMapping(value = "/places/v1/reverse-geocode")
        ReverseGeocodeResponse getReverseGeocode(@RequestHeader(AUTHORIZATION) String token, @RequestParam String latlng);
    }
}
