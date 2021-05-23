package starsrus.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DecimalFormat;

import starsrus.Main;

public class Contracts {

    public Object[][] getContracts(String aid) {
        // count how many contracts
        String transcountsql = "SELECT COUNT(*) as count \n"
        + "FROM Contracts \n"
        + "WHERE aid = ?";

        int count = -1;
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(transcountsql)) {
        
            pstmt.setString(1,aid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Object[][] res = new Object[count][4];

        String transactionsql = "SELECT title, role, year, value\n"
        + "FROM Contracts \n"
        + "WHERE aid = ?"; 

        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(transactionsql)) {
        
            pstmt.setString(1,aid);
            ResultSet rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                res[i][0] = rs.getString("title");
                res[i][1] = rs.getString("role");
                res[i][2] = rs.getString("year");
                res[i][3] = "$" + new DecimalFormat("#.00").format(rs.getDouble("value"));
                i++;
            }
          
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }
}
