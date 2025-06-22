package com.fhodun.mobinspect.window.add;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.fhodun.mobinspect.DatabaseManager;
import com.fhodun.mobinspect.model.Motorcycle;

public class AddMotorcycle extends AddVehicle {
    public AddMotorcycle(JFrame parent, DatabaseManager dbManager, DefaultTableModel tableModel) {
        super(parent, dbManager, tableModel);
        setTitle("Dodaj motocykl");
    }

    @Override
    protected void onAddButtonClicked() {
        Motorcycle motorcycle = dbManager.addMotorcycle(
                brandField.getText(),
                modelField.getText(),
                yearField.getText(),
                licensePlateField.getText(),
                vinField.getText(),
                "Benzyna", // TODO: engineTypeField.getText()
                500 // TODO: Integer.parseInt(engineCapacityField.getText())
        );
        tableModel.addRow(motorcycle.toRow());
        JOptionPane.showMessageDialog(this, "Motocykl dodany!");
        dispose();
    }
}