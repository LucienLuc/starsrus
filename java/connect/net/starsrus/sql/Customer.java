package starsrus.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import starsrus.Main;

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

    public String getName(int taxid) {
        String nameSql = "SELECT name \n"
        + "FROM Customers "
        + "WHERE taxid = ?";

        String name = "";
    
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(nameSql)) {
            
            pstmt.setInt(1,taxid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                name = rs.getString("name");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return name;
    }

    public String getEmail(int taxid) {
        String emailSql = "SELECT email \n"
        + "FROM Customers "
        + "WHERE taxid = ?";

        String email = "";
    
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(emailSql)) {
            
            pstmt.setInt(1,taxid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                email = rs.getString("email");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return email;
    }

    public String getState(int taxid) {
        String stateSql = "SELECT state \n"
        + "FROM Customers "
        + "WHERE taxid = ?";

        String state = "";
    
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(stateSql)) {
            
            pstmt.setInt(1,taxid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                state = rs.getString("state");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return state;
    }

    public double getBalance(int taxid) {
        String balanceSql = "SELECT balance \n"
        + "FROM MarketAccounts "
        + "WHERE taxid = ?";

        double balance = 0;
    
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(balanceSql)) {
            
            pstmt.setInt(1,taxid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                balance = rs.getDouble("balance");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return balance;
    }




    // Inital balance calculated by getting daily closing balance from earliest day in db
    public double getInitialMonthlyBalance(int taxid) {
        String balanceSql = "SELECT balance \n"
        + "FROM DailyBalance "
        + "WHERE taxid = ?"
        + "ORDER BY date ASC";

        double balance = 0;
    
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(balanceSql)) {
            
            pstmt.setInt(1,taxid);
            ResultSet rs = pstmt.executeQuery();
            
            // only grab first one, if exists
            while (rs.next()) {
                balance = rs.getDouble("balance");

                break;
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return balance;
    }

    // Final balance calculated by getting daily closing balance from latest day in db
    public double getFinalMonthlyBalance(int taxid) {
        String balanceSql = "SELECT balance \n"
        + "FROM DailyBalance "
        + "WHERE taxid = ?"
        + "ORDER BY date DESC";

        double balance = 0;
    
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(balanceSql)) {
            
            pstmt.setInt(1,taxid);
            ResultSet rs = pstmt.executeQuery();
            
            // only grab first one, if exists
            while (rs.next()) {
                balance = rs.getDouble("balance");
                break;
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return balance;
    }

    // returns number of stock trades
    // to calculate total commissions
    public int countStockTrades(int taxid) {
        String countSql = "SELECT COUNT(*) AS count \n"
        + "FROM Transactions "
        + "WHERE taxid = ? AND type = ? OR type = ?"
        + "ORDER BY date DESC";

        int count = 0;
    
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(countSql)) {
            
            pstmt.setInt(1,taxid);
            pstmt.setString(2,"b");
            pstmt.setString(3,"s");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                count = rs.getInt("count");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }

    public double getEarnings(int taxid) {
        String countSql = "SELECT SUM(earnings) AS earnings \n"
        + "FROM Transactions "
        + "WHERE taxid = ?";

        double earnings = 0;
    
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(countSql)) {
            
            pstmt.setInt(1,taxid);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                earnings = rs.getDouble("earnings");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return earnings;
    }

}
