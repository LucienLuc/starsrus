package starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sys {
    String getToday() {
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
}
