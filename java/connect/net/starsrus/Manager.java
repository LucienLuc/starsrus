package starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Manager {

    // only for sample data
    public void register(String name, String username, String password, String address, 
    String state, String phone, String email,int taxid, String ssn){ 

        String managerSql = "INSERT INTO Managers VALUES(\n"
        + "	?, ?, ?, ?, ?, ?, ?, ?, ? \n"
        + ");"; 

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(managerSql)) {
            
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
        }
        return;
    }

    // attempt a login
    // returns -1 if login fails
    // returns the user's taxid if login successful
    public boolean login(String username, String password) {

        String loginSql = "SELECT COUNT(*) as count \n"
        + "FROM Managers "
        + "WHERE username = ? AND password = ?";

        int count = 0;
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(loginSql)) {
            
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                count = rs.getInt("count");
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (count == 1) {
            System.out.println("Manager successfully logged in as " + username);
            return true;
        }
        else {
            System.out.println("Invalid username and password combination.");
            return false;
        }
    }
}
