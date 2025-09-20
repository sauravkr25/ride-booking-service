package com.shareride.ridebooking.client.olamaps;

import com.shareride.ridebooking.client.olamaps.response.OlaDirectionsResponse;
import com.shareride.ridebooking.client.olamaps.response.OlaGeocodeResponse;
import com.shareride.ridebooking.client.olamaps.response.OlaReverseGeocodeResponse;

public interface IOlaMapsClient {

    OlaGeocodeResponse getGeocode(String address);

    OlaReverseGeocodeResponse getReverseGeocode(Double lat, Double lng);

    OlaDirectionsResponse getDirections(Double originLat, Double originLng, Double destLat, Double destLng,
                                        String mode, Boolean trafficMetadata, Boolean steps, Boolean alternatives);
}
