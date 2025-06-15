package com.fhodun.mobinspect.model;

public class Motorcycle extends Vehicle {
    private String engineType;
    private int engineCapacity;

    public Motorcycle(String brand, String model, String year, String licensePlate, String vin, String engineType,
            int engineCapacity) {
        super(brand, model, year, licensePlate, vin);
        this.engineType = engineType;
        this.engineCapacity = engineCapacity;
    }

    // region Getters
    public String getEngineType() {
        return engineType;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }
    // endregion
}
