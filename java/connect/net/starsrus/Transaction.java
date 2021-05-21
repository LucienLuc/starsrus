package starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction {
    void storeTransaction(String date, int taxid, char type, int shares, String aid, double price, double total) {
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
}
