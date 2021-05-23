package starsrus.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import starsrus.Main;

public class MarketAccount {

    int taxid;

    public MarketAccount(int taxid) {
        this.taxid = taxid;
    }

    public void addInterest(double rate) {
        
    }

    public double getBalance() {
        String balancesql = "SELECT balance \n"
        + "FROM MarketAccounts \n"
        + "WHERE taxid = ?";

        double res = -1;
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(balancesql)) {
        
            pstmt.setDouble(1,taxid);
            ResultSet rs  = pstmt.executeQuery();

            while (rs.next()) {
                res = rs.getDouble("balance");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public void deposit(double amount) {
        String depositsql = "UPDATE MarketAccounts SET balance = balance + ? \n"
        + "WHERE taxid = ?";

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(depositsql)) {
        
            pstmt.setDouble(1,amount);
            pstmt.setInt(2,taxid);
            pstmt.executeUpdate();

            conn.close();
            System.out.println("Deposited " + Double.toString(amount) + " into " + Integer.toString(taxid));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Sys s = new Sys();
        String today = s.getToday();
        // Store transaction
        Transaction t = new Transaction();
        t.storeDepositTransaction(today, taxid, amount);
    }

    public boolean withdraw(double amount) {

        // Forbid withdrawing amount greater than current balance
        double balance = getBalance();
        if (amount > balance) {
            return false;
        }

        String depositsql = "UPDATE MarketAccounts SET balance = balance - ? \n"
        + "WHERE taxid = ?";

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(depositsql)) {
        
            pstmt.setDouble(1,amount);
            pstmt.setInt(2,taxid);
            pstmt.executeUpdate();

            conn.close();
            System.out.println("Withdrew " + Double.toString(amount) + " from " + Integer.toString(taxid));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Sys s = new Sys();
        String today = s.getToday();
        // Store transaction
        Transaction t = new Transaction();
        t.storeWithdrawTransaction(today, taxid, -1*amount);
        return true;
    }
}
