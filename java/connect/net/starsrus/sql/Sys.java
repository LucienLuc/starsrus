package starsrus.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import starsrus.Main;

public class Sys {
    public String getToday() {
        String todaysql = "SELECT today \n"
        + "FROM System ";

        String res = "";
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(todaysql)) {
        
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                res = rs.getString("today");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    boolean isOpen() {
        String marketsql = "SELECT open \n"
        + "FROM System \n";
        boolean res = false;
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(marketsql)) {
        
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
               res = rs.getBoolean("open");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }
}
