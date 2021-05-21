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
        
        return false;
    }

    // sells quantity number of stock aid from price buyPrice
    // returns amount of money gained from selling
    public double sell(String aid, int quantity, double buyPrice) {
        return 0;
    }
}
