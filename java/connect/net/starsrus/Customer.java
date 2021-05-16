package starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer {
    public void register(String name, String username, String password, String address, 
    String state, String phone, String email,String taxid, String ssn, double deposit){ 

        String customerSql = "INSERT INTO Customers VALUES(\n"
        + "	?, ?, ?, ?, ?, ?, ?, ?, ? \n"
        + ");"; 

        String marketSql = "INSERT INTO MarketAccounts VALUES(\n"
        + "	?, ? \n"
        + ");"; 

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(customerSql)) {
            
            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, address);
            pstmt.setString(5, state);
            pstmt.setString(6, phone);
            pstmt.setString(7, email);
            pstmt.setString(8, taxid);
            pstmt.setString(9, ssn);
            pstmt.executeUpdate();

            System.out.println("Registered user " + username);

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(marketSql)) {
            
            pstmt.setString(1, taxid);
            pstmt.setDouble(2, deposit);
            pstmt.executeUpdate();

            System.out.println("Created Market Account for " + username);

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return;
    }
}
