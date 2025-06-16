package com.fhodun.mobinspect.model;

public class Motorcycle extends Vehicle {
    public static final String DB_TABLE_NAME = "motorcycles";

    private String engineType;
    private int engineCapacity;

    public Motorcycle(String brand, String model, String year, String licensePlate, String vin, String engineType,
            int engineCapacity) {
        super(brand, model, year, licensePlate, vin);
        this.engineType = engineType;
        this.engineCapacity = engineCapacity;
    }

    @Override
    public String toString() {
        return """
                Motorcycle{
                    brand='%s',
                    model='%s',
                    year='%s',
                    licensePlate='%s',
                    vin='%s',
                    engineType='%s',
                    engineCapacity=%d
                }""".formatted(
                getBrand(),
                getModel(),
                getYear(),
                getLicensePlate(),
                getVin(),
                engineType,
                engineCapacity);
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
