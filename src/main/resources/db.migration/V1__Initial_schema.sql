-- V1: Initial schema for the ShareRide Ride & Booking Service.
-- This script creates the core tables for managing rides, vehicles, and booking requests.

-- First, ensure the PostGIS extension is enabled in your database.
-- This is essential for storing and querying geographic locations.
CREATE EXTENSION IF NOT EXISTS postgis;


-- 1. Create the Vehicles table
-- This table stores information about the vehicles registered by drivers.
CREATE TABLE vehicles (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    -- The owner_id is the user's UUID from the separate Identity Service.
                          owner_id UUID NOT NULL,
                          make VARCHAR(100) NOT NULL,
                          model VARCHAR(100) NOT NULL,
                          color VARCHAR(50) NOT NULL,
                          registration_number VARCHAR(20) NOT NULL UNIQUE,
                          created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                          updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
COMMENT ON TABLE vehicles IS 'Stores information about vehicles registered by drivers.';


-- 2. Create the Rides table
-- This is the central table for storing all ride offers posted by drivers.
CREATE TABLE rides (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    -- The driver_id is the user's UUID from the Identity Service.
    -- NOTE: There is no FOREIGN KEY constraint here because the 'users' table lives in a different microservice.
                       driver_id UUID NOT NULL,
    -- A ride must be associated with a specific vehicle.
    -- ON DELETE RESTRICT prevents a vehicle from being deleted if it has active/future rides.
                       vehicle_id UUID NOT NULL REFERENCES vehicles(id) ON DELETE RESTRICT,
    -- We use the GEOGRAPHY type from PostGIS for accurate, real-world distance calculations.
    -- SRID 4326 is the standard for GPS coordinates (WGS 84).
                       origin GEOGRAPHY(POINT, 4326) NOT NULL,
                       destination GEOGRAPHY(POINT, 4326) NOT NULL,
    -- Stores the encoded route polyline string from the Ola Maps API.
                       route_polyline TEXT NOT NULL,
                       departure_time TIMESTAMPTZ NOT NULL,
                       available_seats INTEGER NOT NULL CHECK (available_seats > 0),
                       status VARCHAR(50) NOT NULL,
                       created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                       updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
COMMENT ON TABLE rides IS 'The central table for storing all ride offers posted by drivers.';


-- 3. Create the Ride Requests table
-- This table links a rider (a User) to a Ride they want to join.
CREATE TABLE ride_requests (
                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    -- A ride request is always associated with a ride in this database.
    -- ON DELETE CASCADE means if a ride is deleted, all its requests are automatically deleted too.
                               ride_id UUID NOT NULL REFERENCES rides(id) ON DELETE CASCADE,
    -- The rider_id is the user's UUID from the Identity Service.
                               rider_id UUID NOT NULL,
                               status VARCHAR(50) NOT NULL,
                               created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                               updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    -- A rider can only request to join the same ride once.
                               UNIQUE(ride_id, rider_id)
);
COMMENT ON TABLE ride_requests IS 'Links a rider (a User) to a Ride they want to join.';


-- 4. Create Performance Indexes
-- Indexes are crucial for making database lookups fast, especially on large tables.

-- A GIST index is a special type for geospatial data, making proximity searches (like ST_DWithin) extremely fast.
CREATE INDEX rides_origin_geog_idx ON rides USING GIST (origin);
-- A standard B-tree index on departure_time will speed up searches based on a time window.
CREATE INDEX rides_departure_time_idx ON rides (departure_time);

-- Indexes to quickly find all requests for a specific ride or made by a specific rider.
CREATE INDEX ride_requests_ride_id_idx ON ride_requests(ride_id);
CREATE INDEX ride_requests_rider_id_idx ON ride_requests(rider_id);

-- Index to quickly find the vehicle for a specific user.
CREATE INDEX vehicles_owner_id_idx ON vehicles(owner_id);