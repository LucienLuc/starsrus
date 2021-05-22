package starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.time.LocalDate;

public class Debug {

    // ppen market by changing current date to tomorrow
    // returns the new date 
    public String openMarket() {
        Sys s = new Sys();
        String today = s.getToday();

        String newDate = LocalDate.parse(today).plusDays(1).toString();

        Main.setToday(newDate);
        return newDate;
    }

    // Store daily balance of all market accounts
    // Store daily closing price of stocks
    public void closeMarket() {

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

        Sys s = new Sys();
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

    }
}
