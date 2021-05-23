package starsrus.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import starsrus.Main;

import java.time.LocalDate;

public class Debug {

    // open market by changing current date to tomorrow
    // returns if valid
    public boolean openMarket() {
        Sys s = new Sys();
        boolean open = s.isOpen();

        if (open) {
            return false;
        }

        // Set market to open
        String systemupdatesql = "UPDATE System SET open = ? \n ";
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(systemupdatesql)) {
            
            pstmt.setBoolean(1, true);

            pstmt.executeUpdate();

            System.out.println("Opened Market");
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String today = s.getToday();

        String newDate = LocalDate.parse(today).plusDays(1).toString();

        Main.setToday(newDate);
        return true;
    }

    // Store daily balance of all market accounts
    // Store daily closing price of stocks
    public boolean closeMarket() {

        // check if market is already closed
        Sys s = new Sys();
        boolean open = s.isOpen();

        if (!open) {
            return false;
        }

        // Set market to close
        String systemupdatesql = "UPDATE System SET open = ? \n ";
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(systemupdatesql)) {
            
            pstmt.setBoolean(1, false);

            pstmt.executeUpdate();

            System.out.println("Closed Market");
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // get all market account's taxid
        String accountcountsql = "SELECT taxid, balance \n"
        + "FROM MarketAccounts \n";

        ArrayList<Integer> taxidList = new ArrayList<Integer>(); 
        ArrayList<Double> balanceList = new ArrayList<Double>(); 

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(accountcountsql)) {
        
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
               taxidList.add(rs.getInt("taxid"));
               balanceList.add(rs.getDouble("balance"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //insert into table
        String balanceInsertionSql = "INSERT INTO DailyBalance VALUES(\n"
        + "	?, ?, ? \n"
        + ");"; 

        String today = s.getToday();
        for (int i = 0; i < taxidList.size(); i++) {

            int taxid = taxidList.get(i);
            double balance = balanceList.get(i);

            try (Connection conn = DriverManager.getConnection(Main.url);
                PreparedStatement pstmt = conn.prepareStatement(balanceInsertionSql)) {
                
                pstmt.setInt(1, taxid);
                pstmt.setString(2, today);
                pstmt.setDouble(3, balance);
                pstmt.executeUpdate();

                conn.close();
                System.out.println("Closing balance for " + Integer.toString(taxid) + " on " + today + " is " + Double.toString(balance));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        // get all stock aid
        String stockcountsql = "SELECT aid, currprice \n"
        + "FROM Actors \n";

        ArrayList<String> aidList = new ArrayList<String>(); 
        ArrayList<Double> priceList = new ArrayList<Double>(); 

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(stockcountsql)) {
        
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
               aidList.add(rs.getString("aid"));
               priceList.add(rs.getDouble("currprice"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String stockInsertionSql = "INSERT INTO DailyStock VALUES(\n"
        + "	?, ?, ? \n"
        + ");"; 
        for (int i = 0; i < aidList.size(); i++) {

            String aid = aidList.get(i);
            double price = priceList.get(i);

            try (Connection conn = DriverManager.getConnection(Main.url);
                PreparedStatement pstmt = conn.prepareStatement(stockInsertionSql)) {
                
                pstmt.setString(1, aid);
                pstmt.setString(2, today);
                pstmt.setDouble(3, price);
                pstmt.executeUpdate();

                conn.close();
                System.out.println("Closing stock price for " + aid + " on " + today + " is " + Double.toString(price));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }

    public void setStockPrice(String aid, double price) {
        String stockupdatesql = "UPDATE Actors SET currprice = ? WHERE aid = ?\n ";

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(stockupdatesql)) {
            
            pstmt.setDouble(1, price);
            pstmt.setString(2, aid);

            pstmt.executeUpdate();

            System.out.println("Set new price of stock " + aid + " to " + Double.toString(price));
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setDate(String newDate) {
        Sys s = new Sys();
        String today = s.getToday();

        LocalDate ldtoday = LocalDate.parse(today);
        LocalDate ldnewDate = LocalDate.parse(newDate);

        while(!ldtoday.equals(ldnewDate)) {

            // Store daily information
            closeMarket();

            // Go to next day
            openMarket();
            String newDay = s.getToday();
            ldtoday = LocalDate.parse(newDay);
        }
    }
}
