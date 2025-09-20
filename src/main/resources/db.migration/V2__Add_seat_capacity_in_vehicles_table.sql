-- V2: Add seat_capacity to the vehicles table.
-- This column is essential for knowing the maximum number of passengers a vehicle can carry.

ALTER TABLE vehicles
    ADD COLUMN seat_capacity INTEGER NOT NULL DEFAULT 4 CHECK (seat_capacity > 0);

COMMENT ON COLUMN vehicles.seat_capacity IS 'The total number of seats in the vehicle, including the driver.';