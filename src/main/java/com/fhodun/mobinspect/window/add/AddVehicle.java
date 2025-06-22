package com.fhodun.mobinspect.window.add;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import com.fhodun.mobinspect.DatabaseManager;

public abstract class AddVehicle extends JDialog {
    protected DatabaseManager dbManager;
    protected DefaultTableModel tableModel;

    protected JTextField brandField = new JTextField();
    protected JTextField modelField = new JTextField();
    protected JTextField yearField = new JTextField();
    protected JTextField licensePlateField = new JTextField();
    protected JTextField vinField = new JTextField();

    public AddVehicle(JFrame parent, DatabaseManager dbManager, DefaultTableModel tableModel) {
        super(parent, "Dodaj pojazd", true);

        this.dbManager = dbManager;
        this.tableModel = tableModel;

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

    protected abstract void onAddButtonClicked();
}
