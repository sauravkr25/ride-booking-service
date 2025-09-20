package com.shareride.ridebooking.api.response;

import com.shareride.ridebooking.dto.Route;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DirectionResponse {

    private Integer numberOfRoutes;
    private List<Route> routes;
}
