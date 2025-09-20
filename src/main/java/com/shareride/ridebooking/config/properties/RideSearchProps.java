package com.shareride.ridebooking.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ride-search")
public class RideSearchProps {

    private long originRadiusMeters;
    private long destinationRadiusMeters;
    private long timeWindowMinutes;
    private double minimumOverlapPercentage;
}
