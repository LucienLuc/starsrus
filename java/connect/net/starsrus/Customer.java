package starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer {

    public boolean register(String name, String username, String password, String address, 
    String state, String phone, String email,int taxid, String ssn, double deposit){ 

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
            pstmt.setInt(8, taxid);
            pstmt.setString(9, ssn);
            pstmt.executeUpdate();

            System.out.println("Registered user " + username);

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
			return false;
        }

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(marketSql)) {
            
            pstmt.setInt(1, taxid);
            pstmt.setDouble(2, deposit);
            pstmt.executeUpdate();

            System.out.println("Created Market Account for " + username);

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
			return false;
        }
        return true;
    }
    // attempt a login
    // returns -1 if login fails
    // returns the user's taxid if login successful
    public int login(String username, String password) {

        String loginSql = "SELECT taxid \n"
        + "FROM Customers "
        + "WHERE username = ? AND password = ?";
        
        int taxid = -1;

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(loginSql)) {
            
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                taxid = rs.getInt("taxid");
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (taxid != -1) {
            System.out.println("Successfully logged in as " + username);
            return taxid;
        }
        else {
            System.out.println("Invalid username and password combination.");
            return -1;
        }
    }
}
