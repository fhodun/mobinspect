package com.fhodun.mobinspect;

import java.time.LocalDate;

import com.fhodun.mobinspect.model.*;

class TechnicalInspection {
    private Vehicle vehicle;
    private long odometerReading;
    private LocalDate validFrom;
    private LocalDate validUntil;

    public TechnicalInspection(Vehicle vehicle, long odometerReading, LocalDate validFrom, LocalDate validUntil) {
        this.vehicle = vehicle;
        this.odometerReading = odometerReading;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
    }

    public String toString() {
        return String.format("Technical Inspection for %s %s (%s) - Odometer: %d km, Valid from: %s to %s",
                vehicle.getBrand(), vehicle.getModel(), vehicle.getYear(), odometerReading, validFrom, validUntil);
    }

    // region Getters
    public Vehicle getVehicle() {
        return vehicle;
    }

    public long getOdometerReading() {
        return odometerReading;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }
    // endregion
}