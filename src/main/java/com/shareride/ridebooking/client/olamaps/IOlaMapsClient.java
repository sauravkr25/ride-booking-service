package com.shareride.ridebooking.client.olamaps;

import com.shareride.ridebooking.client.olamaps.response.GeocodeResponse;
import com.shareride.ridebooking.client.olamaps.response.ReverseGeocodeResponse;

public interface IOlaMapsClient {

    GeocodeResponse getGeocode(String address);

    ReverseGeocodeResponse getReverseGeocode(Double lat, Double lng);
}
