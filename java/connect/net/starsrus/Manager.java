package starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.time.LocalDate;

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

    public void addInterest(double percent) {
        String loginSql = "SELECT taxid \n"
        + "FROM MarketAccounts ";

        ArrayList<Integer> taxidList = new ArrayList<Integer>();

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(loginSql)) {
            
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                taxidList.add(rs.getInt("taxid"));
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // calculate average monthly balance for each account
        ArrayList<Double> avgBalList = new ArrayList<Double>();
        for (int i = 0; i < taxidList.size(); i++ ) {
            
            String avgBalSql = "SELECT AVG(balance) as avg \n"
            + "FROM DailyBalance "
            + "WHERE taxid = ?";

            int aid = taxidList.get(i);

            try (Connection conn = DriverManager.getConnection(Main.url);
                PreparedStatement pstmt = conn.prepareStatement(avgBalSql)) {
                
                pstmt.setInt(1,aid);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    avgBalList.add(rs.getDouble("avg"));
                }

                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        for (int i = 0; i < taxidList.size(); i++) {
            int taxid = taxidList.get(i);
            double balance = avgBalList.get(i);

            double interestMoney = balance * percent * 0.01;
            interestMoney = (double) Math.round(interestMoney * 100) / 100;

            // Update Market account balance
            String systemupdatesql = "UPDATE MarketAccounts SET balance = balance + ? WHERE taxid = ?";

            try (Connection conn = DriverManager.getConnection(Main.url);
                PreparedStatement pstmt = conn.prepareStatement(systemupdatesql)) {
                
                pstmt.setDouble(1, interestMoney);
                pstmt.setInt(2, taxid);
                pstmt.executeUpdate();

                System.out.println("Added interest amount " + Double.toString(interestMoney) + " for user " + Integer.toString(taxid));
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            //Store transaction
            Sys s = new Sys();
            String today = s.getToday();
            Transaction t = new Transaction();
            t.storeInterestTransaction(today, taxid, interestMoney);

        }
    }

    // returns list of taxid who have traded at least 1000 shares in last month
    public ArrayList<Integer> getActiveCustomers() {

        String activesql = "SELECT taxid \n"
        + "FROM (\n"
            + "SELECT taxid, SUM(shares) as sum \n"
            + "FROM Transactions \n"
            + "WHERE type = 'b' OR type = 's' \n"
            + "GROUP BY taxid )"
        + "WHERE sum > 999";

        ArrayList<Integer> list = new ArrayList<Integer>();

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(activesql)) {
        
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("taxid"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    // returns list of taxid who have earned at least $10000 (from stocks and interest)
    public ArrayList<Integer> getDTER() {

        String dtersql = "SELECT taxid \n"
        + "FROM (\n"
            + "SELECT taxid, SUM(earnings) as sum \n"
            + "FROM Transactions \n"
            + "GROUP BY taxid )"
        + "WHERE sum > 10000";

        ArrayList<Integer> list = new ArrayList<Integer>();

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(dtersql)) {
        
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("taxid"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

}
