package com.fhodun.mobinspect.model;

public class Car extends Vehicle {
    private String fuelType;
    private int numberOfDoors;

    public Car(String brand, String model, String year, String licensePlate, String vin, String fuelType,
            int numberOfDoors) {
        super(brand, model, year, licensePlate, vin);
        this.fuelType = fuelType;
        this.numberOfDoors = numberOfDoors;
    }

    // region Getters
    public String getFuelType() {
        return fuelType;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }
    // endregion
}
