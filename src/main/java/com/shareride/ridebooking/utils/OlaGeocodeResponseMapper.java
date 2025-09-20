package com.shareride.ridebooking.utils;

import com.shareride.ridebooking.client.olamaps.response.AddressComponent;
import com.shareride.ridebooking.client.olamaps.response.GeocodingResult;
import com.shareride.ridebooking.client.olamaps.response.Geometry;
import com.shareride.ridebooking.client.olamaps.response.OlaGeocodeResponse;
import com.shareride.ridebooking.dto.GeocodeDto;
import com.shareride.ridebooking.dto.LocationCoordinate;
import com.shareride.ridebooking.dto.PlaceDetails;

import java.util.Collections;
import java.util.List;

public class OlaGeocodeResponseMapper {

    private OlaGeocodeResponseMapper () {}

    public static GeocodeDto toGeocodeDto(OlaGeocodeResponse response) {

        if (response == null || response.getGeocodingResults() == null) {
            return GeocodeDto.builder()
                    .numberOfResults(0)
                    .results(Collections.emptyList())
                    .build();
        }

        List<PlaceDetails> placeDetailsList = response.getGeocodingResults().stream()
                .map(result -> toPlaceDetails(result))
                .toList();

        return GeocodeDto.builder()
                .numberOfResults(placeDetailsList.size())
                .results(placeDetailsList)
                .build();
    }

    private static PlaceDetails toPlaceDetails(GeocodingResult geocodingResult) {
        if (geocodingResult == null) {
            return  null;
        }

        PlaceDetails.PlaceDetailsBuilder builder = PlaceDetails.builder()
                .name(geocodingResult.getName())
                .address(geocodingResult.getFormattedAddress())
                .types(geocodingResult.getTypes())
                .location(getLocation(geocodingResult.getGeometry()))
                .locationType(geocodingResult.getGeometry() == null ? null : geocodingResult.getGeometry().getLocationType());

        for (AddressComponent component : geocodingResult.getAddressComponents()) {
            if (component.getTypes() == null || component.getTypes().isEmpty()) continue;

            List<String> types = component.getTypes();
            String value = component.getLongName();

            if (types.contains("country")) {
                builder.country(value);
            } else if (types.contains("administrative_area_level_1")) {
                builder.state(value);
            } else if (types.contains("administrative_area_level_2")) {
                builder.district(value);
            } else if (types.contains("administrative_area_level_3")) {
                builder.subDistrict(value);
            } else if (types.contains("locality")) {
                builder.city(value);
            } else if (types.contains("sublocality")) {
                builder.subLocality(value);
            } else if (types.contains("sublocality_level_3")) {
                builder.landmark(value);
            } else if (types.contains("neighborhood")) {
                builder.neighborhood(value);
            } else if (types.contains("street_address")) {
                builder.street(value);
            } else if (types.contains("postal_code")) {
                builder.postalCode(value);
            }
        }

        return builder.build();
    }

    private static LocationCoordinate getLocation(Geometry geometry) {
        if (geometry == null) {
            return  null;
        }

        return LocationCoordinate.builder()
                .latitude(geometry.getLocation().getLat())
                .longitude(geometry.getLocation().getLng())
                .build();
    }
}
