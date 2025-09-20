package com.shareride.ridebooking.utils;

import com.shareride.ridebooking.client.olamaps.response.Leg;
import com.shareride.ridebooking.client.olamaps.response.OlaDirectionsResponse;
import com.shareride.ridebooking.dto.DirectionDto;
import com.shareride.ridebooking.dto.LocationCoordinate;
import com.shareride.ridebooking.dto.Route;
import com.shareride.ridebooking.dto.Step;

import java.util.Collections;
import java.util.List;

public class OlaDirectionsResponseMapper {

    private OlaDirectionsResponseMapper() {}

    public static DirectionDto toDirectionDto(OlaDirectionsResponse response) {
        // Return a builder with empty route info if the response is invalid
        if (response == null || response.getRoutes() == null) {
            return DirectionDto.builder()
                    .numberOfRoutes(0)
                    .routes(Collections.emptyList())
                    .build();
        }

        List<Route> dtoRoutes = response.getRoutes().stream()
                .map(route -> toDtoRoute(route))
                .toList();

        return DirectionDto.builder()
                .numberOfRoutes(dtoRoutes.size())
                .routes(dtoRoutes)
                .build();
    }

    private static Route toDtoRoute(com.shareride.ridebooking.client.olamaps.response.Route olaRoute) {
        if (olaRoute == null) {
            return null;
        }
        List<Leg> olaLegs = olaRoute.getLegs();
        Leg firstLeg = olaLegs == null || olaLegs.isEmpty() ? null : olaLegs.get(0);
        List<com.shareride.ridebooking.client.olamaps.response.Step> olaSteps = firstLeg.getSteps();

        return Route.builder()
                .routePolyline(olaRoute.getOverviewPolyline())
                .travelAdvisory(olaRoute.getTravelAdvisory())
                .distance(firstLeg.getDistance())
                .readableDistance(firstLeg.getReadableDistance())
                .duration(firstLeg.getDuration())
                .readableDuration(firstLeg.getReadableDuration())
                .startLocation(new LocationCoordinate(firstLeg.getStartLocation().getLat(), firstLeg.getStartLocation().getLng()))
                .endLocation(new LocationCoordinate(firstLeg.getEndLocation().getLat(), firstLeg.getEndLocation().getLng()))
                .steps(olaSteps == null ? null : olaSteps.stream()
                        .map(step -> toDtoStep(step))
                        .toList())
                .build();
    }

    private static Step toDtoStep(com.shareride.ridebooking.client.olamaps.response.Step olaStep) {
        if (olaStep == null) {
            return null;
        }

        return Step.builder()
                .instructions(olaStep.getInstructions())
                .maneuver(olaStep.getManeuver())
                .distance(olaStep.getDistance())
                .readableDistance(olaStep.getReadableDistance())
                .duration(olaStep.getDuration())
                .readableDuration(olaStep.getReadableDuration())
                .startLocation(new LocationCoordinate(olaStep.getStartLocation().getLat(), olaStep.getStartLocation().getLng()))
                .endLocation(new LocationCoordinate(olaStep.getEndLocation().getLat(), olaStep.getEndLocation().getLng()))
                .build();
    }
}
