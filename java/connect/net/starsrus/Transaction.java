package starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class Transaction {
    void storeStockTransaction(String date, int taxid, char type, int shares, String aid, double price, double total) {
        String transactionsql = "INSERT INTO Transactions VALUES(\n"
        + "	?, ?, ?, ?, ?, ?, ?\n"
        + ");"; 
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(transactionsql)) {
            
            pstmt.setString(1, date);
            pstmt.setInt(2, taxid);
            pstmt.setString(3, String.valueOf(type));
            pstmt.setInt(4, shares);
            pstmt.setString(5, aid);
            pstmt.setDouble(6, price);
            pstmt.setDouble(7, total);

            pstmt.executeUpdate();
            System.out.println("Stored transaction of " + Integer.toString(shares) + " shares of " + aid + " for " + Integer.toString(taxid) + 
            " at $" + Double.toString(price) + ". Total $" + Double.toString(total));

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void storeDepositTransaction(String date, int taxid, double total) {
        String transactionsql = "INSERT INTO Transactions VALUES(\n"
        + "	?, ?, ?, ?, ?, ?, ?\n"
        + ");"; 
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(transactionsql)) {
            
            pstmt.setString(1, date);
            pstmt.setInt(2, taxid);
            pstmt.setString(3, "d");
            pstmt.setNull(4, Types.VARCHAR);
            pstmt.setNull(5, Types.VARCHAR);
            pstmt.setNull(6, Types.VARCHAR);
            pstmt.setDouble(7, total);

            pstmt.executeUpdate();
            System.out.println("Stored deposit transaction of " + Double.toString(total) + " for " + Integer.toString(taxid));

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void storeWithdrawTransaction(String date, int taxid, double total) {
        String transactionsql = "INSERT INTO Transactions VALUES(\n"
        + "	?, ?, ?, ?, ?, ?, ?\n"
        + ");"; 
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(transactionsql)) {
            
            pstmt.setString(1, date);
            pstmt.setInt(2, taxid);
            pstmt.setString(3, "w");
            pstmt.setNull(4, Types.VARCHAR);
            pstmt.setNull(5, Types.VARCHAR);
            pstmt.setNull(6, Types.VARCHAR);
            pstmt.setDouble(7, total);

            pstmt.executeUpdate();
            System.out.println("Stored withdraw transaction of " + Double.toString(total) + " for " + Integer.toString(taxid));

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Object[][] getTransactionHistory(int taxid) {
        // count number of transactions
        String transcountsql = "SELECT COUNT(*) as count \n"
        + "FROM Transactions \n"
        + "WHERE taxid = ?";

        int count = -1;
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(transcountsql)) {
        
            pstmt.setInt(1,taxid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Object[][] res = new Object[count][6];

        String transactionsql = "SELECT date, type, shares, aid, price, total \n"
        + "FROM Transactions \n"
        + "WHERE taxid = ?"; 

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(transactionsql)) {
        
            pstmt.setInt(1,taxid);
            ResultSet rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                    res[i][0] = rs.getString("date");
                    char temptype = rs.getString("type").charAt(0);
                    if (temptype == 'b') {
                        res[i][1] = "Buy";
                    }
                    else if (temptype == 's'){
                        res[i][1] = "Sell";
                    }
                    else if (temptype == 'w'){
                        res[i][1] = "Withdraw";
                    }
                    else if (temptype == 'd'){
                        res[i][1] = "Deposit";
                    }
                    else {
                        System.out.println("Error");
                    }

                    int tempshares = rs.getInt("shares");
                    if (rs.wasNull()) {
                        res[i][2] = "";
                    }
                    else {
                        res[i][2] = tempshares;
                    }
                    res[i][3] = rs.getString("aid");

                    double tempprice = rs.getDouble("price");
                    if (rs.wasNull()) {
                        res[i][4] = "";
                    }
                    else {
                        res[i][4] = "$" + new DecimalFormat("#.00").format(tempprice);
                    }
    
                    res[i][5] = "$" + new DecimalFormat("#.00").format(rs.getDouble("total"));
                    i++;
                }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    void storeInterestTransaction(String date, int taxid, double amount) {
        String transactionsql = "INSERT INTO Transactions VALUES(\n"
        + "	?, ?, ?, ?, ?, ?, ?\n"
        + ");"; 
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(transactionsql)) {
            
            pstmt.setString(1, date);
            pstmt.setInt(2, taxid);
            pstmt.setString(3, "i");
            pstmt.setNull(4, Types.VARCHAR);
            pstmt.setNull(5, Types.VARCHAR);
            pstmt.setNull(6, Types.VARCHAR);
            pstmt.setDouble(7, amount);

            pstmt.executeUpdate();
            System.out.println("Stored interest transaction of " + Double.toString(amount) + " for " + Integer.toString(taxid));

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
