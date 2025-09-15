package com.shareride.ridebooking.utils;

import com.shareride.ridebooking.dto.LocationCoordinate;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;


public class JtsUtil {

    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public static Point convertToPoint(LocationCoordinate locationCoordinate) {
        Coordinate jtsCoordinate = new Coordinate(locationCoordinate.getLongitude(), locationCoordinate.getLatitude());
        return geometryFactory.createPoint(jtsCoordinate);
    }

    public static LocationCoordinate convertToLocationCoordinate(Point point) {
        return LocationCoordinate.builder()
                .latitude(point.getY())   // Y = latitude
                .longitude(point.getX())  // X = longitude
                .build();
    }
}
