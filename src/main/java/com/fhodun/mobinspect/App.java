package com.fhodun.mobinspect;

import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.fhodun.mobinspect.model.*;
import com.fhodun.mobinspect.window.add.AddCar;
import com.fhodun.mobinspect.window.add.AddMotorcycle;

public class App {
    static DefaultTableModel tableModel;
    static DatabaseManager dbManager;
    public static JFrame frame;

    public static void main(String[] args) {
        dbManager = new DatabaseManager();

        List<String> columnsList = Vehicle.GetColumns();
        String[] columns = columnsList.toArray(new String[0]);

        List<Vehicle> vehicles = dbManager.getAllVehicles();
        Object[][] data = vehicles.stream()
                .map(Vehicle::toRow)
                .toArray(Object[][]::new);

        tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);

        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem editItem = new JMenuItem("Edytuj");
        editItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) table.getValueAt(selectedRow, 0);
                // TODO: Implement edit functionality
            }
        });
        popupMenu.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Usuń");
        deleteItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) table.getValueAt(selectedRow, 0);
                dbManager.deleteCar(id);
                tableModel.removeRow(selectedRow);
            }
        });
        popupMenu.add(deleteItem);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < table.getRowCount()) {
                        table.setRowSelectionInterval(row, row);
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });

        frame = new JFrame("System zarzadzania badaniami technicznymi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addCarButton = new JButton("Dodaj samochód");
        JButton addMotorcycleButton = new JButton("Dodaj motocykl");

        addCarButton.addActionListener(e -> onAddCarBtnClicked());
        addMotorcycleButton.addActionListener(e -> onAddMotorcycleBtnClicked());

        buttonPanel.add(addCarButton);
        buttonPanel.add(addMotorcycleButton);

        frame.add(buttonPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setVisible(true);
    }

    private static Object onAddCarBtnClicked() {
        new AddCar(frame, dbManager, tableModel);
        return null;
    }

    private static Object onAddMotorcycleBtnClicked() {
        new AddMotorcycle(frame, dbManager, tableModel);
        return null;
    }
}