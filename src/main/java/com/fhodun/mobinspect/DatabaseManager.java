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
        addCar("Toyota", "Camry", "2020", "ABC123", "1HGBH41JXMN109186", "Gasoline", 4);
        addCar("Honda", "Civic", "2019", "XYZ789", "2HNYD2H59AH000001", "Gasoline", 4);
        addCar("Ford", "Focus", "2021", "LMN456", "3FADP4FJ5JM000002", "Gasoline", 4);
    }

    private void initializeMotorcycles() {
        if (getTableCount(Motorcycle.DB_TABLE_NAME) != 0) {
            return;
        }

        addMotorcycle("Yamaha", "MT-07", "2020", "MOTO123", "JYARM06E0LA000001", "Parallel Twin", 689);
        addMotorcycle("Honda", "CBR500R", "2019", "MOTO456", "MLHPC4460D5200002", "Parallel Twin", 471);
        addMotorcycle("Kawasaki", "Ninja 400", "2021", "MOTO789", "JKAEX8A18MDA00003", "Parallel Twin", 399);
    }
    // endregion

    // region Add Methods
    public Car addCar(String brand, String model, String year, String licensePlate, String vin,
            String fuelType, int numberOfDoors) {
        String sql = """
                INSERT INTO %s (brand, model, year, license_plate, vin, fuel_type, number_of_doors)
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """.formatted(Car.DB_TABLE_NAME);

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, brand);
            pstmt.setString(2, model);
            pstmt.setString(3, year);
            pstmt.setString(4, licensePlate);
            pstmt.setString(5, vin);
            pstmt.setString(6, fuelType);
            pstmt.setInt(7, numberOfDoors);

            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                return new Car(
                        id,
                        brand,
                        model,
                        year,
                        licensePlate,
                        vin,
                        fuelType,
                        numberOfDoors);
            } else {
                throw new SQLException("Nie udało się uzyskać ID nowego rekordu.");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting car: " + e.getMessage());
            return null;
        }
    }

    public Motorcycle addMotorcycle(String brand, String model, String year, String licensePlate, String vin,
            String engineType, int engineCapacity) {
        String sql = """
                INSERT INTO %s (brand, model, year, license_plate, vin, engine_type, engine_capacity)
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """.formatted(Motorcycle.DB_TABLE_NAME);

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, brand);
            pstmt.setString(2, model);
            pstmt.setString(3, year);
            pstmt.setString(4, licensePlate);
            pstmt.setString(5, vin);
            pstmt.setString(6, engineType);
            pstmt.setInt(7, engineCapacity);

            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                return new Motorcycle(
                        id,
                        brand,
                        model,
                        year,
                        licensePlate,
                        vin,
                        engineType,
                        engineCapacity);
            } else {
                throw new SQLException("Nie udało się uzyskać ID nowego rekordu.");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting motorcycle: " + e.getMessage());
            return null;
        }
    }
    // endregion

    // region Getters
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

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM %s".formatted(Car.DB_TABLE_NAME))) {
            while (rs.next()) {
                Car car = new Car(
                        rs.getInt("id"),
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
                        rs.getInt("id"),
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

    public Car getCarById(int id) {
        String sql = "SELECT * FROM %s WHERE id = ?".formatted(Car.DB_TABLE_NAME);
        Car car = null;

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                car = new Car(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("year"),
                        rs.getString("license_plate"),
                        rs.getString("vin"),
                        rs.getString("fuel_type"),
                        rs.getInt("number_of_doors"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching car by ID: " + e.getMessage());
        }

        return car;
    }

    public Motorcycle getMotorcycleById(int id) {
        String sql = "SELECT * FROM %s WHERE id = ?".formatted(Motorcycle.DB_TABLE_NAME);
        Motorcycle motorcycle = null;

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                motorcycle = new Motorcycle(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("year"),
                        rs.getString("license_plate"),
                        rs.getString("vin"),
                        rs.getString("engine_type"),
                        rs.getInt("engine_capacity"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching motorcycle by ID: " + e.getMessage());
        }

        return motorcycle;
    }
    // endregion

    // region Delete Methods
    public boolean deleteCar(int id) {
        String sql = "DELETE FROM %s WHERE id = ?".formatted(Car.DB_TABLE_NAME);

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting car: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteMotorcycle(int id) {
        String sql = "DELETE FROM %s WHERE id = ?".formatted(Motorcycle.DB_TABLE_NAME);

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting motorcycle: " + e.getMessage());
            return false;
        }
    }
    // endregion
}
