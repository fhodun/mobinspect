package com.fhodun.mobinspect.model;

public class Car extends Vehicle {
    public static final String DB_TABLE_NAME = "cars";

    private String fuelType;
    private int numberOfDoors;

    public Car(String brand, String model, String year, String licensePlate, String vin, String fuelType,
            int numberOfDoors) {
        super(brand, model, year, licensePlate, vin);
        this.fuelType = fuelType;
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public String toString() {
        return """
                Car{
                    brand='%s',
                    model='%s',
                    year='%s',
                    licensePlate='%s',
                    vin='%s',
                    fuelType='%s',
                    numberOfDoors='%d',
                }""".formatted(getBrand(), getModel(), getYear(), getLicensePlate(), getVin(), fuelType, numberOfDoors);
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
