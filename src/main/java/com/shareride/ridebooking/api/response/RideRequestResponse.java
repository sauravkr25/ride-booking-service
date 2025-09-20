package com.shareride.ridebooking.api.response;

import com.shareride.ridebooking.domain.RideRequestDomain;
import com.shareride.ridebooking.enums.RequestStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class RideRequestResponse {
    private UUID requestId;
    private UUID rideId;
    private RequestStatus status;
    private Instant createdAt;

    public static RideRequestResponse from(RideRequestDomain rideRequestDomain) {
        return RideRequestResponse.builder()
                .requestId(rideRequestDomain.getRequestId())
                .rideId(rideRequestDomain.getRideId())
                .status(rideRequestDomain.getStatus())
                .createdAt(rideRequestDomain.getCreatedAt())
                .build();
    }
}
