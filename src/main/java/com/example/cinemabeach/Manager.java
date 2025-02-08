package com.example.cinemabeach;

import java.sql.*;

public class Manager {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1111";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void createMovie(String name, int duration) {
        String checkSql = "SELECT COUNT(*) FROM movies WHERE name = ?";
        String insertSql = "INSERT INTO movies (name, duration) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            // Check if movie already exists
            checkStmt.setString(1, name);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                System.out.println("⚠️ Movie already exists: " + name);
                return; // Stop inserting duplicate
            }

            // Insert new movie
            insertStmt.setString(1, name);
            insertStmt.setInt(2, duration);
            insertStmt.executeUpdate();
            System.out.println("✅ Movie added: " + name);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void readMovies() {
        String sql = "SELECT * FROM movies";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("🎬 Movie ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Duration: " + rs.getInt("duration") + " min");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMovie(int id, String newName, int newDuration) {
        String sql = "UPDATE movies SET name = ?, duration = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setInt(2, newDuration);
            pstmt.setInt(3, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Movie updated: ID " + id);
            } else {
                System.out.println("❌ Movie not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMovie(int id) {
        String sql = "DELETE FROM movies WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Movie deleted: ID " + id);
            } else {
                System.out.println("❌ Movie not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearMovies() {
        String sql = "DELETE FROM movies";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("🗑️ All movies deleted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}