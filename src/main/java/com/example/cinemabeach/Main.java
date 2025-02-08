package com.example.cinemabeach;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Manager.clearMovies();

        System.out.println("✅ Connecting to PostgreSQL...");


        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "1111")) {
            System.out.println("✅ Connected to PostgreSQL!");
        } catch (SQLException e) {
            System.out.println("❌ Connection failed!");
            e.printStackTrace();
            return;
        }

        System.out.println("\n📋 Movies from Database:");
        Manager.readMovies();

        System.out.println("\n➕ Adding movies to Database...");
        Manager.createMovie("Avatar", 162);
        Manager.createMovie("The Dark Knight", 152);

        System.out.println("\n📋 Movies After Insert:");
        Manager.readMovies();

        System.out.println("\n✏️ Updating a movie...");
        Manager.updateMovie(53, "Inception (Updated)", 150);

        System.out.println("\n📋 Movies After Update:");
        Manager.readMovies();

        System.out.println("\n🗑️ Deleting a movie...");
        Manager.deleteMovie(2);

        System.out.println("\n📋 Movies After Deletion:");
        Manager.readMovies();
    }
}