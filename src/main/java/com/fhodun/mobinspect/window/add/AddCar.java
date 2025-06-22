package com.fhodun.mobinspect.window.add;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.fhodun.mobinspect.DatabaseManager;
import com.fhodun.mobinspect.model.Car;

public class AddCar extends AddVehicle {
    public AddCar(JFrame parent, DatabaseManager dbManager, DefaultTableModel tableModel) {
        super(parent, dbManager, tableModel);
        setTitle("Dodaj samochód");
    }

    @Override
    protected void onAddButtonClicked() {
        Car car = dbManager.addCar(
                brandField.getText(),
                modelField.getText(),
                yearField.getText(),
                licensePlateField.getText(),
                vinField.getText(),
                "Benzyna", // TODO: fuelTypeField.getText(),
                4 // TODO: Integer.parseInt(numberOfDoorsField.getText())
        );
        tableModel.addRow(car.toRow());
        JOptionPane.showMessageDialog(this, "Samochód dodany!");
        dispose();
    }
}