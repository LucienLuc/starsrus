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

        // check if sufficient funds ($20 commision)
        if (balance < price * quantity + 20) {
            return false;
        }

        // update stockaccount table
        // see if user already has bought stocks of this aid
        String stockcountsql = "COUNT(*)\n"
        + "FROM Stocks \n"
        + "WHERE taxid = ? AND aid = ?";

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(stockcountsql)) {
        
            pstmt.setDouble(1,quantity);
            pstmt.setInt(2,taxid);
            pstmt.executeUpdate();

            conn.close();
            System.out.println("Deposited " + Double.toString(quantity) + " into " + Integer.toString(taxid));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // withdraw money from market account

        // store transaction
        return true;
    }

    // sells quantity number of stock aid from price buyPrice
    // returns amount of money gained from selling
    public double sell(String aid, int quantity, double buyPrice) {
        return 0;
    }
}
