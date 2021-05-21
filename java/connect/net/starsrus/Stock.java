package starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Stock {



    double getPrice(String aid) {
        // String depositsql = "SELECT currprice \n"
        // + "FROM Actors"
        // + "WHERE taxid = ?";

        // try (Connection conn = DriverManager.getConnection(Main.url);
        //     PreparedStatement pstmt = conn.prepareStatement(depositsql)) {
        
        //     pstmt.setDouble(1,amount);
        //     pstmt.setInt(2,taxid);
        //     pstmt.executeUpdate();

        //     conn.close();
        //     System.out.println("Deposited " + Double.toString(amount) + " into " + Integer.toString(taxid));
        // } catch (SQLException e) {
        //     System.out.println(e.getMessage());
        // }
        return 0;
    }

    String getName(String aid) {
        return "";
    }
    String getDOB(String aid) {
        return "";
    }
}
