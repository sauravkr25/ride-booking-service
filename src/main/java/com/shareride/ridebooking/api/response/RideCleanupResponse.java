package com.shareride.ridebooking.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RideCleanupResponse {

    private String status;
    private String message;
    private long cancelledRidesCount;
}
