package starsrus.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import starsrus.Main;

import java.sql.ResultSet;

public class Movie {
    public void insertMovie(String mid, String title, int year, String genre, double rating, int revenue) {
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

    public Object[][] listMovies() {

        // count number of movies
        String countsql = "SELECT COUNT(*) as count FROM Movies";

        int count = 0;
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(countsql)) {
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    
        // create list
        String listsql = "SELECT mid, title, year, genre, rating, revenue \n"
        + "	FROM Movies ";

        Object[][] list = new Object[count][6];
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(listsql)) {
            
            ResultSet rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                list[i][0] = rs.getString("mid");
                list[i][1] = rs.getString("title");
                list[i][2] = Integer.toString(rs.getInt("year"));
                list[i][3] = rs.getString("genre");
                list[i][4] = Double.toString(rs.getDouble("rating"));
                list[i][5] = "$" + Integer.toString(rs.getInt("revenue"));
                i++;
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public Object[][] topMovies(int start_year, int end_year, double rating) {

        // count number of movies
        String countsql = "SELECT COUNT(*) as count \n"
        + "FROM Movies \n"
        + "WHERE year >= ? AND year <= ? AND rating >= ?";

        int count = 0;
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(countsql)) {

            pstmt.setInt(1, start_year);
            pstmt.setInt(2, end_year);
            pstmt.setDouble(3, rating);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // create list
        String listsql = "SELECT mid, title, year, genre, rating, revenue \n"
        + "FROM Movies \n"
        + "WHERE year >= ? AND year <= ? AND rating >= ?";

        Object[][] list = new Object[count][6];
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(listsql)) {

            pstmt.setInt(1, start_year);
            pstmt.setInt(2, end_year);
            pstmt.setDouble(3, rating);
            
            ResultSet rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                list[i][0] = rs.getString("mid");
                list[i][1] = rs.getString("title");
                list[i][2] = Integer.toString(rs.getInt("year"));
                list[i][3] = rs.getString("genre");
                list[i][4] = Double.toString(rs.getDouble("rating"));
                list[i][5] = "$" + Integer.toString(rs.getInt("revenue"));
                i++;
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public double getRating(int id) {
        String countsql = "SELECT rating \n"
        + "FROM Movies \n"
        + "WHERE mid = ?";

        double rating = -1;
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(countsql)) {

            pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rating = rs.getDouble("rating");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rating;
    }
}
