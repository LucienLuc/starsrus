package starsrus.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import starsrus.Main;

public class StockAccount {
    int taxid;

    public StockAccount(int taxid) {
        this.taxid = taxid;
    }

    // FOR SAMPLE DATA ONLY
    public void giveStock(int shares, String aid, double buyprice) {
        String stocksql = "INSERT INTO Stocks VALUES(\n"
        + "	?, ?, ?, ? \n"
        + ");"; 
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(stocksql)) {
            
            pstmt.setInt(1, taxid);
            pstmt.setInt(2, shares);
            pstmt.setString(3, aid);
            pstmt.setDouble(4, buyprice);

            pstmt.executeUpdate();
            System.out.println("Gave " + Integer.toString(shares) + " shares of " + aid + " for " + Integer.toString(taxid) + 
            " at $" + Double.toString(buyprice));

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Store into transactions table
        // Decided to simply make all these stocks buy transactions
        // Sys system = new Sys();
        // String today = system.getToday();
        // Transaction t = new Transaction();
        // double total = (shares * buyprice + 20) * -1;
        // t.storeStockTransaction(today, taxid, 'b', shares, aid, buyprice, total);
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
        // see if user already has bought stocks of this aid at this price
        String stockcountsql = "SELECT COUNT(*) as count \n"
        + "FROM Stocks \n"
        + "WHERE taxid = ? AND aid = ? AND buyprice = ?";

        boolean found = false;
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(stockcountsql)) {
        
            pstmt.setInt(1,taxid);
            pstmt.setString(2,aid);
            pstmt.setDouble(3, price);
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
            return false;
        }

        // update existing tuple if found
        if (found) {
            String stockupdatesql = "UPDATE Stocks SET shares = shares + ? \n "
            + "WHERE taxid = ? AND aid = ? AND buyprice = ?";
            try (Connection conn = DriverManager.getConnection(Main.url);
                PreparedStatement pstmt = conn.prepareStatement(stockupdatesql)) {
                
                pstmt.setInt(1, quantity);
                pstmt.setInt(2, taxid);
                pstmt.setString(3, aid);
                pstmt.setDouble(4, price);

                pstmt.executeUpdate();
                System.out.println("Bought (Update) " + Integer.toString(quantity) + " shares of " + aid + " for " + Integer.toString(taxid));

                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        // otherwise create new tuple
        else {
            String stockinsertsql = "INSERT INTO Stocks VALUES(\n"
            + "	?, ?, ?, ? \n"
            + ");"; 
            try (Connection conn = DriverManager.getConnection(Main.url);
                PreparedStatement pstmt = conn.prepareStatement(stockinsertsql)) {
                
                pstmt.setInt(1, taxid);
                pstmt.setInt(2, quantity);
                pstmt.setString(3, aid);
                pstmt.setDouble(4, price);

                pstmt.executeUpdate();
                System.out.println("Bought " + Integer.toString(quantity) + " shares of " + aid + " for " + Integer.toString(taxid));

                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        // subtract money from market account
        String costsql = "UPDATE MarketAccounts SET balance = balance - ? \n"
        + "WHERE taxid = ?";

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(costsql)) {
        
            pstmt.setDouble(1,transactionCost);
            pstmt.setInt(2,taxid);
            pstmt.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // store transaction
        Sys system = new Sys();
        String today = system.getToday();
        Transaction t = new Transaction();
        double format = transactionCost * -1;
        t.storeStockTransaction(today, taxid, 'b', quantity, aid, price, format, -20); //net earnings -20 becuase commission

        return true;
        
    }

    // sells quantity number of stock aid from price buyPrice
    // returns amount of money gained from selling
    public boolean sell(String aid, int quantity, double buyPrice) {
        // check if user owns at least this amount of stock
        String stockcountsql = "SELECT shares \n"
        + "FROM Stocks \n"
        + "WHERE taxid = ? AND aid = ? AND buyprice = ?";

        int count = 0;
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(stockcountsql)) {
        
            pstmt.setInt(1,taxid);
            pstmt.setString(2,aid);
            pstmt.setDouble(3, buyPrice);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("shares");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (count < quantity) {
            return false;
        }

        //get currprice of stock
        Stock stock = new Stock();
        double currPrice = stock.getPrice(aid);

        double earnings = (currPrice - buyPrice) * quantity - 20; //$20 commission
        double gain = currPrice * quantity - 20; // $20 commission

        // delete tuple from Stock table
        if (count == quantity) {
            String stockdelete = "DELETE FROM Stocks WHERE taxid = ? AND aid = ? AND buyprice = ?";
            try (Connection conn = DriverManager.getConnection(Main.url);
                PreparedStatement pstmt = conn.prepareStatement(stockdelete)) {
            
                pstmt.setInt(1,taxid);
                pstmt.setString(2,aid);
                pstmt.setDouble(3, buyPrice);
                pstmt.executeUpdate();
                
                conn.close();
                System.out.println("User " + Integer.toString(taxid) + " sold (deleted)" + Integer.toString(quantity) +  " shares of " + aid);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        //update and subtract
        else {
            String stockupdate = "UPDATE Stocks SET shares = shares - ? \n "
            + "WHERE taxid = ? AND aid = ? AND buyprice = ?";
            try (Connection conn = DriverManager.getConnection(Main.url);
                PreparedStatement pstmt = conn.prepareStatement(stockupdate)) {
                
                pstmt.setInt(1, quantity);
                pstmt.setInt(2, taxid);
                pstmt.setString(3, aid);
                pstmt.setDouble(4, buyPrice);

                pstmt.executeUpdate();
                System.out.println("User " + Integer.toString(taxid) + " sold (Update) " + Integer.toString(quantity) + " shares of " + aid);

                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        //add money into market account
        String profitsql = "UPDATE MarketAccounts SET balance = balance + ? \n"
        + "WHERE taxid = ?";

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(profitsql)) {
        
            pstmt.setDouble(1,gain);
            pstmt.setInt(2,taxid);
            pstmt.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //store transaction
        Sys system = new Sys();
        String today = system.getToday();
        Transaction t = new Transaction();
        t.storeStockTransaction(today, taxid, 's', quantity, aid, currPrice, gain, earnings);

        return true;
    }
}
