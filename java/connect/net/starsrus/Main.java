package starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;

public class Main {
     /**
     * Connect to our database
     */
    public static String url = "jdbc:sqlite:/Users/lucienluc/Downloads/Spring 2021/CMPSC174a/starsrus/db/starsrus.db";

    public static void connect() {
        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void setup() {
        String customerTable = "CREATE TABLE IF NOT EXISTS Customers (\n"
        + "	name varchar(20) NOT NULL,\n"
        + " username varchar(20) UNIQUE NOT NULL, \n"
        + " password varchar(20) NOT NULL, \n"
        + " address varchar(30) NOT NULL, \n"
        + "	state char(2) NOT NULL,\n"
        + " phone char(10) NOT NULL, \n"
        + " email varchar(20) NOT NULL, \n"
        + " taxid varchar(9) PRIMARY KEY, \n"
        + " ssn char(9) NOT NULL \n"
        + ");";

        // do we need accountid?
        String marketAccountTable = "CREATE TABLE IF NOT EXISTS MarketAccounts (\n"
        + "	taxid varchar(9) PRIMARY KEY NOT NULL,\n"
        + " balance real NOT NULL, \n"
        + " FOREIGN KEY (taxid) REFERENCES Customers \n"
        + ");";

        String actorTable = "CREATE TABLE IF NOT EXISTS Actors (\n"
        + "	aid char(3) NOT NULL,\n"
        + " currprice real NOT NULL, \n"
        + " name varchar(20) NOT NULL, \n"
        + " dob date NOT NULL \n"
        + ");";

        String contractsTable = "CREATE TABLE IF NOT EXISTS Contracts (\n"
        + "	aid char(3) PRIMARY KEY NOT NULL,\n"
        + " title varchar(20) NOT NULL, \n"
        + " role varchar(10) NOT NULL, \n"
        + " year char(4) NOT NULL, \n"
        + " value real NOT NULL, \n"
        + " FOREIGN KEY (aid) REFERENCES Actors"
        + ");";

        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            stmt.execute(customerTable);
            System.out.println("Created table Customers");

            stmt.execute(marketAccountTable);
            System.out.println("Created table MarketAccounts");

            stmt.execute(actorTable);
            System.out.println("Created table Actors");

            stmt.execute(contractsTable);
            System.out.println("Created table Contracts");

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void insertSampleData() {

        //Customer info
        try {
            // path from /connect folder becuase this is where program is executed
            File f = new File("./sampledata/customers.txt");
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String data = s.nextLine();
                String[] p = data.split(",");

                Customer c = new Customer();
                c.register(p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7], p[8], Integer.parseInt(p[9]));
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        connect();
        setup();
        insertSampleData();
    }
}