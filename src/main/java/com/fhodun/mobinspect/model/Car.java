package com.fhodun.mobinspect.model;

public class Car extends Vehicle {
    public static final String DB_TABLE_NAME = "cars";

    private String fuelType;
    private int numberOfDoors;

    public Car(int id, String brand, String model, String year, String licensePlate, String vin, String fuelType,
            int numberOfDoors) {
        super(id, brand, model, year, licensePlate, vin);
        this.fuelType = fuelType;
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public String toString() {
        return """
                Car{
                    id='%d',
                    brand='%s',
                    model='%s',
                    year='%s',
                    licensePlate='%s',
                    vin='%s',
                    fuelType='%s',
                    numberOfDoors='%d',
                }""".formatted(
                getId(),
                getBrand(),
                getModel(),
                getYear(),
                getLicensePlate(),
                getVin(),
                getFuelType(),
                getNumberOfDoors());
    }

    @Override
    public Object[] toRow() {
        return new Object[] {
                getId(),
                getBrand(),
                getModel(),
                getYear(),
                getLicensePlate(),
                getVin(),
                "Samochód"
        };
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
