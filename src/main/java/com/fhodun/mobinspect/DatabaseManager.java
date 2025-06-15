package com.fhodun.mobinspect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:db.sqlite";
    private static final boolean DEBUG = true;

    public DatabaseManager() {
        createVehicleTableIfNotExists();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void createVehicleTableIfNotExists() {
        String sql = """
                CREATE TABLE IF NOT EXISTS vehicles (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    brand TEXT NOT NULL,
                    model TEXT NOT NULL,
                    year TEXT NOT NULL,
                    license_plate TEXT NOT NULL,
                    vin TEXT NOT NULL,
                    fuel_type TEXT,
                    number_of_doors INTEGER
                );
                """;
        String vehiclesSql = """
                INSERT INTO vehicles (brand, model, year, license_plate, vin, fuel_type, number_of_doors)
                VALUES
                ('Toyota', 'Camry', '2020', 'ABC123', '1HGBH41JXMN109186', 'Gasoline', 4),
                ('Honda', 'Civic', '2019', 'XYZ789', '2HNYD2H59AH000001', 'Gasoline', 4),
                ('Ford', 'Focus', '2021', 'LMN456', '3FADP4FJ5JM000002', 'Gasoline', 4);
                """;

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            String checkSql = "SELECT COUNT(*) AS count FROM vehicles";
            try (ResultSet rs = stmt.executeQuery(checkSql)) {
                if (rs.next() && rs.getInt("count") == 0 && DEBUG) {
                    stmt.execute(vehiclesSql);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public void addCar(Car car) {
        String sql = """
                INSERT INTO vehicles (brand, model, year, license_plate, vin, fuel_type, number_of_doors)
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, car.getBrand());
            pstmt.setString(2, car.getModel());
            pstmt.setString(3, car.getYear());
            pstmt.setString(4, car.getLicensePlate());
            pstmt.setString(5, car.getVin());
            pstmt.setString(6, car.getFuelType());
            pstmt.setInt(7, car.getNumberOfDoors());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting car: " + e.getMessage());
        }
    }

    public List<Car> getAllVehicles() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Car car = new Car(
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("year"),
                        rs.getString("license_plate"),
                        rs.getString("vin"),
                        rs.getString("fuel_type"),
                        rs.getInt("number_of_doors"));
                cars.add(car);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching cars: " + e.getMessage());
        }

        return cars;
    }
}
