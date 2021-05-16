package starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Actor {
    public Actor(String aid, double currprice, String name, String dob) {

        String marketSql = "INSERT INTO Actors VALUES(\n"
        + "	?, ?, ?, ? \n"
        + ");"; 
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(marketSql)) {
        
        pstmt.setString(1, aid);
        pstmt.setDouble(2, currprice);
        pstmt.setString(3, name);
        pstmt.setString(4, dob);
        pstmt.executeUpdate();

        System.out.println("Created Actor Stock for " + name);

        conn.close();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    }
}
