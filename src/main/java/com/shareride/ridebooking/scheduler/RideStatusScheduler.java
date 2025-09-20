package com.shareride.ridebooking.scheduler;

import com.shareride.ridebooking.service.RideService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
//@EnableScheduling
public class RideStatusScheduler {

    private final RideService rideService;

    public RideStatusScheduler(RideService rideService) {
        this.rideService = rideService;
    }

    /**
     * A scheduled job that runs automatically to clean up past rides.
     */
    @Scheduled(cron = "0 */5 * * * *")
    public void cleanupPastRides() {
        // This method will be executed automatically by Spring's scheduler.
        rideService.cancelPastScheduledRides();
    }
}
