package starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class Debug {

    // Store daily balance of all market accounts
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
    }
}
