package com.fhodun.mobinspect.window;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import com.fhodun.mobinspect.DatabaseManager;
import com.fhodun.mobinspect.model.Motorcycle;

public class AddMotorcycle extends JFrame {
    private DatabaseManager dbManager;
    private DefaultTableModel model;

    private JTextField brandField = new JTextField();
    private JTextField modelField = new JTextField();
    private JTextField yearField = new JTextField();
    private JTextField licensePlateField = new JTextField();
    private JTextField vinField = new JTextField();

    public AddMotorcycle(DatabaseManager dbManager, DefaultTableModel model) {
        this.dbManager = dbManager;
        this.model = model;

        setTitle("Dodaj motocykl");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));

        formPanel.add(new JLabel("Marka:"));
        formPanel.add(brandField);

        formPanel.add(new JLabel("Model:"));
        formPanel.add(modelField);

        formPanel.add(new JLabel("Rok:"));
        formPanel.add(yearField);

        formPanel.add(new JLabel("Tablica rejestracyjna:"));
        formPanel.add(licensePlateField);

        formPanel.add(new JLabel("VIN:"));
        formPanel.add(vinField);

        add(formPanel, BorderLayout.CENTER);

        JButton addButton = new JButton("Dodaj");
        addButton.addActionListener(e -> onAddButtonClicked());

        add(addButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void onAddButtonClicked() {
        Motorcycle motorcycle = new Motorcycle(
                brandField.getText(),
                modelField.getText(),
                yearField.getText(),
                licensePlateField.getText(),
                vinField.getText(),
                "Benzyna", // TODO:
                4 // TODO:
        );

        dbManager.addMotorcycle(motorcycle);
        model.addRow(new Object[] {
                motorcycle.getBrand(),
                motorcycle.getModel(),
                motorcycle.getYear(),
                motorcycle.getLicensePlate(),
                motorcycle.getVin()
        });

        JOptionPane.showMessageDialog(this, "Motocykl dodany!");
        dispose();
    }
}