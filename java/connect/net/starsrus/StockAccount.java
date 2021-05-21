package starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StockAccount {
    int taxid;

    public StockAccount(int taxid) {
        this.taxid = taxid;
    }

    // FOR SAMPLE DATA ONLY
    void giveStock(int shares, String aid) {
        String stocksql = "INSERT INTO Stocks VALUES(\n"
        + "	?, ?, ? \n"
        + ");"; 
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(stocksql)) {
            
            pstmt.setInt(1, taxid);
            pstmt.setInt(2, shares);
            pstmt.setString(3, aid);

            pstmt.executeUpdate();
            System.out.println("Gave " + Integer.toString(shares) + " shares of " + aid + " for " + Integer.toString(taxid));

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // buys quantity number of stock aid 
    // returns true if enough balance
    // false if not enough balance
    public boolean buy(String aid, int quantity) {
        // get current balance
        MarketAccount ma = new MarketAccount(taxid);
        double balance = ma.getBalance();
        
        // get stock current price
        Stock s = new Stock();
        double price = s.getPrice(aid);
        // Stock aid does not exist
        if (price == -1) {
            return false;
        }

        double transactionCost = price * quantity + 20; // $20 commision

        // check if sufficient funds ($20 commision)
        if (balance < transactionCost) {
            return false;
        }
        
        // update stockaccount table
        // see if user already has bought stocks of this aid
        String stockcountsql = "SELECT COUNT(*) as count \n"
        + "FROM Stocks \n"
        + "WHERE taxid = ? AND aid = ?";

        boolean found = false;
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(stockcountsql)) {
        
            pstmt.setInt(1,taxid);
            pstmt.setString(2,aid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int res = rs.getInt("count");
                if (res == 1) {
                    found = true;
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // update existing tuple if found
        if (found) {
            String stockupdatesql = "UPDATE Stocks SET shares = shares + ? \n "
            + "WHERE taxid = ? AND aid = ?";
            try (Connection conn = DriverManager.getConnection(Main.url);
                PreparedStatement pstmt = conn.prepareStatement(stockupdatesql)) {
                
                pstmt.setInt(1, quantity);
                pstmt.setInt(2, taxid);
                pstmt.setString(3, aid);

                pstmt.executeUpdate();
                System.out.println("Bought (Update) " + Integer.toString(quantity) + " shares of " + aid + " for " + Integer.toString(taxid));

                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        // otherwise create new tuple
        else {
            String stockinsertsql = "INSERT INTO Stocks VALUES(\n"
            + "	?, ?, ? \n"
            + ");"; 
            try (Connection conn = DriverManager.getConnection(Main.url);
                PreparedStatement pstmt = conn.prepareStatement(stockinsertsql)) {
                
                pstmt.setInt(1, taxid);
                pstmt.setInt(2, quantity);
                pstmt.setString(3, aid);

                pstmt.executeUpdate();
                System.out.println("Bought " + Integer.toString(quantity) + " shares of " + aid + " for " + Integer.toString(taxid));

                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        // withdraw money from market account
        ma.withdraw(transactionCost);

        // store transaction

        return true;
        
    }

    // sells quantity number of stock aid from price buyPrice
    // returns amount of money gained from selling
    public double sell(String aid, int quantity, double buyPrice) {
        return 0;
    }
}
