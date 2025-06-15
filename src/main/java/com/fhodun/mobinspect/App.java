package com.fhodun.mobinspect;

import java.time.LocalDate;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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

public class App {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();

        List<String> columnsList = Vehicle.GetColumns();
        String[] columns = columnsList.toArray(new String[0]);

        List<Vehicle> vehicles = dbManager.getAllVehicles();

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
        frame.setSize(600, 400);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setVisible(true);
    }
}