package com.fhodun.mobinspect;

import java.util.List;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.fhodun.mobinspect.model.*;
import com.fhodun.mobinspect.window.*;

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

        DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);

        JFrame frame = new JFrame("System zarzadzania badaniami technicznymi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addCarButton = new JButton("Dodaj samochód");
        JButton addMotorcycleButton = new JButton("Dodaj motocykl");

        addCarButton.addActionListener(e -> onAddCarBtnClicked(dbManager, tableModel));
        addMotorcycleButton.addActionListener(e -> onAddMotorcycleBtnClicked(dbManager, tableModel));

        buttonPanel.add(addCarButton);
        buttonPanel.add(addMotorcycleButton);

        frame.add(buttonPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setVisible(true);
    }

    private static Object onAddCarBtnClicked(DatabaseManager dbManager, DefaultTableModel model) {
        AddCar carWindow = new AddCar(dbManager, model);
        return null;
    }

    private static Object onAddMotorcycleBtnClicked(DatabaseManager dbManager, DefaultTableModel model) {
        AddMotorcycle motorcycleWindow = new AddMotorcycle(dbManager, model);
        return null;
    }
}