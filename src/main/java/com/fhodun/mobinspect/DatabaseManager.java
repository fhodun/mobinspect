package com.fhodun.mobinspect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.fhodun.mobinspect.model.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:db.sqlite";
    private static final boolean DEBUG = true;

    public DatabaseManager() {
        initializeTables();
        if (DEBUG) {
            initializeCars();
            initializeMotorcycles();
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // region Initialization Methods
    private void initializeTables() {
        String carSql = """
                CREATE TABLE IF NOT EXISTS %s (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    brand TEXT NOT NULL,
                    model TEXT NOT NULL,
                    year TEXT NOT NULL,
                    license_plate TEXT NOT NULL,
                    vin TEXT NOT NULL,
                    fuel_type TEXT,
                    number_of_doors INTEGER
                );
                """.formatted(Car.DB_TABLE_NAME);
        String motorcycleSql = """
                CREATE TABLE IF NOT EXISTS %s (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    brand TEXT NOT NULL,
                    model TEXT NOT NULL,
                    year TEXT NOT NULL,
                    license_plate TEXT NOT NULL,
                    vin TEXT NOT NULL,
                    engine_type TEXT,
                    engine_capacity INTEGER
                );
                """.formatted(Motorcycle.DB_TABLE_NAME);

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(carSql);
            stmt.execute(motorcycleSql);

        } catch (SQLException e) {
            System.err.println("Error creating or populating tables: " + e.getMessage());
        }
    }

    private void initializeCars() {
        if (getTableCount(Car.DB_TABLE_NAME) != 0) {
            return;
        }
        List<Car> sampleCars = List.of(
                new Car("Toyota", "Camry", "2020", "ABC123", "1HGBH41JXMN109186", "Gasoline", 4),
                new Car("Honda", "Civic", "2019", "XYZ789", "2HNYD2H59AH000001", "Gasoline", 4),
                new Car("Ford", "Focus", "2021", "LMN456", "3FADP4FJ5JM000002", "Gasoline", 4));

        for (Car car : sampleCars) {
            addCar(car);
        }
    }

    private void initializeMotorcycles() {
        if (getTableCount(Motorcycle.DB_TABLE_NAME) != 0) {
            return;
        }
        List<Motorcycle> sampleMotorcycles = List.of(
                new Motorcycle("Yamaha", "MT-07", "2020", "MOTO123", "JYARM06E0LA000001", "Parallel Twin", 689),
                new Motorcycle("Honda", "CBR500R", "2019", "MOTO456", "MLHPC4460D5200002", "Parallel Twin", 471),
                new Motorcycle("Kawasaki", "Ninja 400", "2021", "MOTO789", "JKAEX8A18MDA00003", "Parallel Twin",
                        399));

        for (Motorcycle motorcycle : sampleMotorcycles) {
            addMotorcycle(motorcycle);
        }
    }
    // endregion

    private int getTableCount(String tableName) {
        String sql = "SELECT COUNT(*) AS count FROM " + tableName;
        int count = 0;

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("Error checking " + tableName + " table: " + e.getMessage());
        }

        return count;
    }

    public void addCar(Car car) {
        String sql = """
                INSERT INTO %s (brand, model, year, license_plate, vin, fuel_type, number_of_doors)
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """.formatted(Car.DB_TABLE_NAME);

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

    public void addMotorcycle(Motorcycle motorcycle) {
        String sql = """
                INSERT INTO %s (brand, model, year, license_plate, vin, engine_type, engine_capacity)
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """.formatted(Motorcycle.DB_TABLE_NAME);

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, motorcycle.getBrand());
            pstmt.setString(2, motorcycle.getModel());
            pstmt.setString(3, motorcycle.getYear());
            pstmt.setString(4, motorcycle.getLicensePlate());
            pstmt.setString(5, motorcycle.getVin());
            pstmt.setString(6, motorcycle.getEngineType());
            pstmt.setInt(7, motorcycle.getEngineCapacity());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting motorcycle: " + e.getMessage());
        }
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM %s".formatted(Car.DB_TABLE_NAME))) {
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

    public List<Motorcycle> getAllMotorcycles() {
        List<Motorcycle> motorcycles = new ArrayList<>();

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM %s".formatted(Motorcycle.DB_TABLE_NAME))) {
            while (rs.next()) {
                Motorcycle motorcycle = new Motorcycle(
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("year"),
                        rs.getString("license_plate"),
                        rs.getString("vin"),
                        rs.getString("engine_type"),
                        rs.getInt("engine_capacity"));
                motorcycles.add(motorcycle);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching motorcycles: " + e.getMessage());
        }

        return motorcycles;
    }

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();

        List<Car> cars = getAllCars();
        List<Motorcycle> motorcycles = getAllMotorcycles();

        vehicles.addAll(cars);
        vehicles.addAll(motorcycles);

        return vehicles;
    }
}
