package com.shareride.ridebooking.service.impl;

import com.shareride.ridebooking.domain.RideRequestDomain;
import com.shareride.ridebooking.enums.RequestStatus;
import com.shareride.ridebooking.enums.RideStatus;
import com.shareride.ridebooking.exception.ApplicationException;
import com.shareride.ridebooking.exception.ErrorCodes;
import com.shareride.ridebooking.repository.RideRepository;
import com.shareride.ridebooking.repository.RideRequestRepository;
import com.shareride.ridebooking.repository.entity.Ride;
import com.shareride.ridebooking.repository.entity.RideRequest;
import com.shareride.ridebooking.service.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static com.shareride.ridebooking.enums.RequestStatus.ACCEPTED;
import static com.shareride.ridebooking.enums.RequestStatus.CANCELLED;
import static com.shareride.ridebooking.enums.RequestStatus.REJECTED;
import static com.shareride.ridebooking.utils.Constants.CAUSE;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRepository rideRepository;
    private final RideRequestRepository rideRequestRepository;
    private final Set<RequestStatus> DRIVER_ALLOWED = Set.of(ACCEPTED, REJECTED);
    private final Set<RequestStatus> RIDER_ALLOWED = Set.of(CANCELLED);

    @Override
    public RideRequestDomain createRideRequest(RideRequestDomain rideRequestDomain) {

        UUID riderId = rideRequestDomain.getRiderId();
        UUID rideId = rideRequestDomain.getRideId();

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> ApplicationException.of(ErrorCodes.RIDE_NOT_FOUND));

        validateRideRequest(ride, riderId);
        RideRequest savedRequest = rideRequestRepository.save(createRideRequestEntity(ride, riderId));
        return RideRequestDomain.from(savedRequest);
    }

    // todo: past rides filter should be there
    @Override
    public List<RideRequestDomain> getRideRequests(UUID rideId, UUID driverId, RequestStatus requestStatus) {
        Ride rideFromDB = rideRepository.findById(rideId)
                .orElseThrow(()-> ApplicationException.of(ErrorCodes.RIDE_NOT_FOUND));

        if (!Objects.equals(driverId, rideFromDB.getDriverId())) {
            throw ApplicationException.of(ErrorCodes.FORBIDDEN, Map.of(CAUSE, "User is not the owner of this ride and cannot view its requests."));
        }

        List<RideRequest> rideRequestsFromDB;
        if (!Objects.isNull(requestStatus)) {
            rideRequestsFromDB = rideRequestRepository.findByRideAndStatus(rideFromDB, requestStatus);
        }
        else {
            rideRequestsFromDB = rideRequestRepository.findByRide(rideFromDB);
        }

        return rideRequestsFromDB.stream()
                .map(RideRequestDomain::from)
                .toList();
    }

    @Override
    public RideRequestDomain updateRideRequest(RideRequestDomain rideRequestDomain) {
        RideRequest rideRequestFromDB = rideRequestRepository.findById(rideRequestDomain.getRequestId())
                .orElseThrow(()-> ApplicationException.of(ErrorCodes.RIDE_REQUEST_NOT_FOUND));
        Ride rideFromDB = rideRequestFromDB.getRide();

        boolean isDriver = false;
        boolean isRider = false;

        if (Objects.equals(rideFromDB.getDriverId(),rideRequestDomain.getUserId())) {
            isDriver = true;
        }
        if (Objects.equals(rideRequestFromDB.getRiderId(),rideRequestDomain.getUserId())) {
            isRider = true;
        }
        if (isDriver == isRider) {
            throw ApplicationException.of(ErrorCodes.CONFLICT, Map.of(CAUSE,
                    isDriver ? "User cannot be both rider and driver." : "User must be either rider or driver."));
        }

        if (rideRequestFromDB.getStatus() != RequestStatus.PENDING) {
            throw ApplicationException.of(ErrorCodes.INVALID_STATUS_TRANSITION);
        }

        validateUpdateRideRequest(isDriver, isRider, rideRequestDomain.getStatus());
        rideRequestFromDB.setStatus(rideRequestDomain.getStatus());
        rideRequestRepository.save(rideRequestFromDB);
        return RideRequestDomain.from(rideRequestFromDB);
    }

    private void validateUpdateRideRequest(boolean isDriver, boolean isRider, RequestStatus status) {
        if (isDriver && !DRIVER_ALLOWED.contains(status)) {
            throw ApplicationException.of(ErrorCodes.INVALID_STATUS_TRANSITION);
        }
        if (isRider && !RIDER_ALLOWED.contains(status)) {
            throw ApplicationException.of(ErrorCodes.INVALID_STATUS_TRANSITION);
        }
        if (status == RequestStatus.EXPIRED) {
            throw ApplicationException.of(ErrorCodes.INVALID_STATUS_TRANSITION);
        }
        if (status == RequestStatus.PENDING) {
            throw ApplicationException.of(ErrorCodes.INVALID_STATUS_TRANSITION);
        }
    }

    private void validateRideRequest(Ride ride, UUID riderId) {
        if (ride.getDriverId().equals(riderId)) {
            throw ApplicationException.of(ErrorCodes.CANNOT_REQUEST_OWN_RIDE);
        }
        if (ride.getStatus() != RideStatus.SCHEDULED) {
            throw ApplicationException.of(ErrorCodes.RIDE_NOT_AVAILABLE_FOR_REQUESTS);
        }
        if (ride.getAvailableSeats() <= 0) {
            throw ApplicationException.of(ErrorCodes.RIDE_IS_FULL);
        }
        if (ride.getDepartureTime().isBefore(Instant.now())) {
            throw ApplicationException.of(ErrorCodes.RIDE_HAS_DEPARTED);
        }
        if (rideRequestRepository.existsByRideAndRiderId(ride, riderId)) {
            throw ApplicationException.of(ErrorCodes.DUPLICATE_RIDE_REQUEST);
        }
    }

    private RideRequest createRideRequestEntity(Ride ride, UUID riderId) {
        return RideRequest.builder()
                .ride(ride)
                .riderId(riderId)
                .status(RequestStatus.PENDING)
                .build();
    }
}
