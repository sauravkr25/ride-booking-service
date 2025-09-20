package com.shareride.ridebooking.domain;

import com.shareride.ridebooking.enums.RequestStatus;
import com.shareride.ridebooking.repository.entity.RideRequest;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class RideRequestDomain {

    private UUID requestId;
    private UUID rideId;
    private UUID riderId;
    private RequestStatus status;
    private Instant createdAt;
    private UUID userId;

    public static RideRequestDomain from(RideRequest rideRequest) {
        return RideRequestDomain.builder()
                .requestId(rideRequest.getId())
                .riderId(rideRequest.getRiderId())
                .rideId(rideRequest.getRide().getId())
                .status(rideRequest.getStatus())
                .createdAt(rideRequest.getCreatedAt())
                .build();
    }
}
