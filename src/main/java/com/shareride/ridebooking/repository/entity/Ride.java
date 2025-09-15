package com.shareride.ridebooking.repository.entity;

import com.shareride.ridebooking.enums.RideStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "rides")
@NoArgsConstructor
@AllArgsConstructor
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "driver_id", nullable = false)
    private UUID driverId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    // The 'Point' class from 'org.locationtech.jts.geom' is used to map to PostGIS GEOGRAPHY types.
    // The columnDefinition tells Hibernate how to handle this special type.
    @Column(columnDefinition = "geography(Point,4326)", nullable = false)
    private Point origin;

    @Column(columnDefinition = "geography(Point,4326)", nullable = false)
    private Point destination;

    @Column(name = "route_polyline", columnDefinition = "TEXT", nullable = false)
    private String routePolyline;

    @Column(name = "departure_time", nullable = false)
    private Instant departureTime;

    @Column(name = "available_seats", nullable = false)
    private int availableSeats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RideStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}

