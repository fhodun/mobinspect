package com.fhodun.mobinspect;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

abstract class Vehicle {
    private String brand;
    private String model;
    private String year;
    private String licensePlate;
    private String vin;

    public Vehicle(String brand, String model, String year, String licensePlate, String vin) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.vin = vin;
    }

    public String toString() {
        return String.format("%s %s (%s) - License Plate: %s, VIN: %s",
                brand, model, year, licensePlate, vin);
    }

    // region Setters
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    // endregion

    // region Getters
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

class Car extends Vehicle {
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

class Motorcycle extends Vehicle {
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

public class App {
    public static void main(String[] args) {
        String[] columns = { "Marka", "Model", "Rocznik", "Numer rejestracyjny", "Vin" };

        DatabaseManager dbManager = new DatabaseManager();

        List<Car> vehicles = dbManager.getAllVehicles();

        Object[][] data = new Object[vehicles.size()][columns.length];

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle vehicle = vehicles.get(i);
            data[i][0] = vehicle.getBrand();
            data[i][1] = vehicle.getModel();
            data[i][2] = vehicle.getYear();
            data[i][3] = vehicle.getLicensePlate();
            data[i][4] = vehicle.getVin();
        }

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);

        JFrame frame = new JFrame("System zarzadzania badaniami technicznymi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setVisible(true);
    }
}