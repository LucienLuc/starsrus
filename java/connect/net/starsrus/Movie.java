package starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Movie {
    void insertMovie(String mid, String title, int year, String genre, double rating, int revenue) {
        String moviesql = "INSERT INTO Movies VALUES(\n"
        + "	?, ?, ?, ?, ?, ?\n"
        + ");"; 
        
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(moviesql)) {
        
        pstmt.setString(1, mid);
        pstmt.setString(2, title);
        pstmt.setInt(3, year);
        pstmt.setString(4, genre);
        pstmt.setDouble(5, rating);
        pstmt.setInt(6, revenue);
        pstmt.executeUpdate();

        System.out.println("Created Movie " + title);
        conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
