package com.fhodun.mobinspect.model;

public class Motorcycle extends Vehicle {
    public static final String DB_TABLE_NAME = "motorcycles";

    private String engineType;
    private int engineCapacity;

    public Motorcycle(int id, String brand, String model, String year, String licensePlate, String vin,
            String engineType,
            int engineCapacity) {
        super(id, brand, model, year, licensePlate, vin);
        this.engineType = engineType;
        this.engineCapacity = engineCapacity;
    }

    @Override
    public String toString() {
        return """
                Motorcycle{
                    id='%d',
                    brand='%s',
                    model='%s',
                    year='%s',
                    licensePlate='%s',
                    vin='%s',
                    engineType='%s',
                    engineCapacity='%d'
                }""".formatted(getId(),
                getBrand(),
                getModel(),
                getYear(),
                getLicensePlate(),
                getVin(),
                getEngineType(),
                getEngineCapacity());
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
                "Motocykl"
        };
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