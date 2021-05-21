package starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Stock {
   

    double getPrice(String aid) {
        String depositsql = "SELECT currprice \n"
        + "FROM Actors \n"
        + "WHERE aid = ?";

        double price = -1;
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(depositsql)) {
        
            pstmt.setString(1,aid);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                price = rs.getDouble("currPrice");
            }

            conn.close();
            System.out.println("Got price of " + Double.toString(price) + " for " + aid);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return price;
    }

    String getName(String aid) {
        return "";
    }
    String getDOB(String aid) {
        return "";
    }
}
