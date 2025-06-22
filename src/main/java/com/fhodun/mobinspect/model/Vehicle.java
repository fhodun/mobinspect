package com.fhodun.mobinspect.model;

import java.util.List;

public abstract class Vehicle {
    public static String DB_TABLE_NAME;

    private int id;
    private String brand;
    private String model;
    private String year;
    private String licensePlate;
    private String vin;

    public Vehicle(int id, String brand, String model, String year, String licensePlate, String vin) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.vin = vin;
    }

    public static List<String> GetColumns() {
        List<String> columns = List.of("Id", "Marka", "Model", "Rocznik", "Numer rejestracyjny", "Vin", "Vehicle Type");
        return columns;
    }

    public abstract String toString();

    public Object[] toRow() {
        return new Object[] {
                getId(),
                getBrand(),
                getModel(),
                getYear(),
                getLicensePlate(),
                getVin(),
                this.getClass().getSimpleName()
        };
    };

    // region Setters
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    // endregion

    // region Getters
    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getVin() {
        return vin;
    }
    // endregion
}
