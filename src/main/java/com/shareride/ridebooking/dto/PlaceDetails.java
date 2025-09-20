package com.shareride.ridebooking.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlaceDetails {

    private String name;
    private String address;
    private List<String> types;

    private LocationCoordinate location;
    private String locationType;

    private String country;
    private String state;               // admin_area_level_1
    private String district;            // admin_area_level_2
    private String subDistrict;         // admin_area_level_3
    private String city;                // locality
    private String subLocality;         // sublocality
    private String neighborhood;        // neighborhood
    private String landmark;            // sublocality_level_3 (campus, complex, society, mall)

    private String street;              // street_address
    private String postalCode;

}
