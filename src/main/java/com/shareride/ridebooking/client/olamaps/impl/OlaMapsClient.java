package com.shareride.ridebooking.client.olamaps.impl;

import com.shareride.ridebooking.client.olamaps.IOlaMapsClient;
import com.shareride.ridebooking.client.olamaps.auth.OlaMapsAuthClient;
import com.shareride.ridebooking.client.olamaps.exception.OlaMapsClientException;
import com.shareride.ridebooking.client.olamaps.response.OlaDirectionsResponse;
import com.shareride.ridebooking.client.olamaps.response.OlaGeocodeResponse;
import com.shareride.ridebooking.client.olamaps.response.OlaReverseGeocodeResponse;
import com.shareride.ridebooking.exception.ErrorCodes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.shareride.ridebooking.utils.Constants.AUTHORIZATION;
import static com.shareride.ridebooking.utils.Constants.BEARER_;
import static com.shareride.ridebooking.utils.Constants.CAUSE;
import static com.shareride.ridebooking.utils.Constants.Properties.OLAMAPS_API_BASE_URL;

@Component
public class OlaMapsClient implements IOlaMapsClient {

    private final OlaMapsApi olaMapsApi;
    private final OlaMapsAuthClient olaMapsAuthClient;
    private final List<String> supportedModes = List.of("driving", "walking", "bike", "auto");

    public OlaMapsClient(OlaMapsApi olaMapsApi, OlaMapsAuthClient olaMapsAuthClient) {
        this.olaMapsApi = olaMapsApi;
        this.olaMapsAuthClient = olaMapsAuthClient;
    }

    @Override
    public OlaGeocodeResponse getGeocode(String address) {
        return olaMapsApi.getGeocode( BEARER_ + olaMapsAuthClient.getTokenResponse().getAccessToken(), address);
    }

    @Override
    public OlaReverseGeocodeResponse getReverseGeocode(Double lat, Double lng) {
        return olaMapsApi.getReverseGeocode(BEARER_ + olaMapsAuthClient.getTokenResponse().getAccessToken(), lat + "," + lng);
    }

    @Override
    public OlaDirectionsResponse getDirections(Double originLat, Double originLng, Double destLat, Double destLng,
                                               String mode, Boolean trafficMetadata, Boolean steps, Boolean alternatives) {
        if (!supportedModes.contains(mode)) {
            throw OlaMapsClientException.of(ErrorCodes.BAD_REQUEST, Map.of(CAUSE, "invalid mode in request parameter"));
        }

        return olaMapsApi.getDirections(BEARER_ + olaMapsAuthClient.getTokenResponse().getAccessToken(),
                originLat + "," + originLng, destLat + "," + destLng, mode, trafficMetadata, steps, alternatives);
    }


    @FeignClient(name = "olaMapsApi", url = OLAMAPS_API_BASE_URL)
    public interface OlaMapsApi {

        @GetMapping(value = "/places/v1/geocode")
        OlaGeocodeResponse getGeocode(@RequestHeader(AUTHORIZATION) String token, @RequestParam String address);

        @GetMapping(value = "/places/v1/reverse-geocode")
        OlaReverseGeocodeResponse getReverseGeocode(@RequestHeader(AUTHORIZATION) String token, @RequestParam String latlng);

        @PostMapping(value = "/routing/v1/directions")
        OlaDirectionsResponse getDirections(@RequestHeader(AUTHORIZATION) String token,
                                            @RequestParam String origin, @RequestParam String destination,
                                            @RequestParam String mode,
                                            @RequestParam(value = "traffic_metadata") Boolean trafficMetadata,
                                            @RequestParam Boolean steps,
                                            @RequestParam Boolean alternatives);
    }
}
