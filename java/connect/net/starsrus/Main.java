package starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;

import starsrus.ui.UserInterface; 

public class Main {
     /**
     * Connect to our database
     */

    public static String url = "jdbc:sqlite:/Users/lucienluc/Downloads/Spring 2021/CMPSC174a/starsrus/db/starsrus.db";

    // For testing connection
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

    // Sets system date
    // date must be in YYYY-MM-DD format
    public static void setToday(String date) {
        // Count tuples
        String systemcountsql = "SELECT COUNT(*) as count \n"
        + "FROM System \n";

        boolean found = false;
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(systemcountsql)) {

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
        }

        //update existing tuple
        if (found) {
            String systemupdatesql = "UPDATE System SET today = ? \n ";

            try (Connection conn = DriverManager.getConnection(Main.url);
                PreparedStatement pstmt = conn.prepareStatement(systemupdatesql)) {
                
                pstmt.setString(1, date);

                pstmt.executeUpdate();

                System.out.println("Update today to " + date);
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        // first insert, so insert tuple
        else {
            String sql = "INSERT INTO System VALUES(?, ?)";

            try (Connection conn = DriverManager.getConnection(Main.url);
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, date);
                pstmt.setBoolean(2, true);
                pstmt.executeUpdate();
                
                System.out.println("Set today to " + date);
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void setup() {

        // Stores system date
        // make sure there is only one tuple
        String systemTable = "CREATE TABLE IF NOT EXISTS System (\n"
        + "	today DATE NOT NULL, \n"
        + " open boolean NOT NULL"
        + ");";

        String managerTable = "CREATE TABLE IF NOT EXISTS Managers (\n"
        + "	name varchar(20) NOT NULL,\n"
        + " username varchar(20) UNIQUE NOT NULL, \n"
        + " password varchar(20) NOT NULL, \n"
        + " address varchar(30) NOT NULL, \n"
        + "	state char(2) NOT NULL,\n"
        + " phone char(10) NOT NULL, \n"
        + " email varchar(20) NOT NULL, \n"
        + " taxid int PRIMARY KEY, \n"
        + " ssn char(9) NOT NULL \n"
        + ");";

        String customerTable = "CREATE TABLE IF NOT EXISTS Customers (\n"
        + "	name varchar(20) NOT NULL,\n"
        + " username varchar(20) UNIQUE NOT NULL, \n"
        + " password varchar(20) NOT NULL, \n"
        + " address varchar(30) NOT NULL, \n"
        + "	state char(2) NOT NULL,\n"
        + " phone char(10) NOT NULL, \n"
        + " email varchar(20) NOT NULL, \n"
        + " taxid int PRIMARY KEY, \n"
        + " ssn char(9) NOT NULL \n"
        + ");";

        // do we need accountid?
        String marketAccountTable = "CREATE TABLE IF NOT EXISTS MarketAccounts (\n"
        + "	taxid int NOT NULL,\n"
        + " balance real NOT NULL, \n"
        + " FOREIGN KEY (taxid) REFERENCES Customers \n"
        + ");";

        String dailyBalanceTable = "CREATE TABLE IF NOT EXISTS DailyBalance (\n"
        + "	taxid int NOT NULL,\n"
        + " date DATE NOT NULL, \n"
        + " balance real NOT NULL, \n"
        + " PRIMARY KEY (taxid, date), \n"
        + " FOREIGN KEY (taxid) REFERENCES Customers \n"
        + ");";

        String stockAccountTable = "CREATE TABLE IF NOT EXISTS Stocks (\n"
        + "	taxid int NOT NULL,\n"
        + " shares int NOT NULL, \n"
        + " aid char(3) NOT NULL, \n"
        + " buyprice real NOT NULL, \n"
        + " PRIMARY KEY (taxid, aid, buyprice) \n"
        + " FOREIGN KEY (taxid) REFERENCES Customers \n"
        + " FOREIGN KEY (aid) REFERENCES Actors"
        + ");";

        // how to store daily closing prices?
        // seperate table with daily closing prices
        // (aid, date, closingprice)
        String actorTable = "CREATE TABLE IF NOT EXISTS Actors (\n"
        + "	aid char(3) PRIMARY KEY NOT NULL,\n"
        + " currprice real NOT NULL, \n"
        + " name varchar(20) NOT NULL, \n"
        + " dob DATE NOT NULL \n"
        + ");";

        String dailyStockTable = "CREATE TABLE IF NOT EXISTS DailyStock (\n"
        + "	aid char(3) NOT NULL,\n"
        + " date DATE NOT NULL, \n"
        + " price real NOT NULL, \n"
        + " PRIMARY KEY (aid, date), \n"
        + " FOREIGN KEY (aid) REFERENCES Actors \n"
        + ");";

        String contractsTable = "CREATE TABLE IF NOT EXISTS Contracts (\n"
        + "	aid char(3) NOT NULL,\n"
        + " title varchar(20) NOT NULL, \n"
        + " role varchar(10) NOT NULL, \n"
        + " year YEAR NOT NULL, \n"
        + " value real NOT NULL, \n"
        + " FOREIGN KEY (aid) REFERENCES Actors"
        + ");";

        String transactionsTable =  "CREATE TABLE IF NOT EXISTS Transactions (\n"
        + " date DATE NOT NULL,\n"
        + "	taxid int NOT NULL,\n"
        + " type char NOT NULL, \n" // b/s/d/w
        + " shares int, \n"
        + " aid char(3), \n"
        + " price real, \n"
        + " total real NOT NULL, \n"
        + " FOREIGN KEY (taxid) REFERENCES Customers \n"
        + " FOREIGN KEY (aid) REFERENCES Actors"
        + ");";

        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            stmt.execute(systemTable);
            System.out.println("Created table System");

            stmt.execute(managerTable);
            System.out.println("Created table Managers");

            stmt.execute(customerTable);
            System.out.println("Created table Customers");

            stmt.execute(marketAccountTable);
            System.out.println("Created table MarketAccounts");

            stmt.execute(dailyBalanceTable);
            System.out.println("Created table DailyBalance");
            
            stmt.execute(stockAccountTable);
            System.out.println("Created table Stocks");

            stmt.execute(actorTable);
            System.out.println("Created table Actors");

            stmt.execute(dailyStockTable);
            System.out.println("Created table DailyStock");

            stmt.execute(contractsTable);
            System.out.println("Created table Contracts");

            stmt.execute(transactionsTable);
            System.out.println("Created table Stocks");

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // To store daily balance/stock price from 2013-03-01 to 2013-03-16
        setToday("2013-03-01");
    }

    public static void insertSampleData() {

        // Manager info
        try {
            // path from /connect folder because this is where program is executed
            File f = new File("./sampledata/managers.txt");
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String data = s.nextLine();
                String[] p = data.split(",");

                Manager m = new Manager();
                m.register(p[0], p[1], p[2], p[3], p[4], p[5], p[6], Integer.parseInt(p[7]), p[8]);
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //Customer info
        try {
            // path from /connect folder because this is where program is executed
            File f = new File("./sampledata/customers.txt");
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String data = s.nextLine();
                String[] p = data.split(",");

                Customer c = new Customer();
                c.register(p[0], p[1], p[2], p[3], p[4], p[5], p[6], Integer.parseInt(p[7]), p[8], Integer.parseInt(p[9]));
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Purchased stocks info
        try {
            // path from /connect folder because this is where program is executed
            File f = new File("./sampledata/stocks.txt");
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String data = s.nextLine();
                String[] p = data.split(",");

                StockAccount sa = new StockAccount(Integer.parseInt(p[0]));
                sa.giveStock(Integer.parseInt(p[1]),p[2], Double.parseDouble(p[3]));
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Actor and stock info
        try {
            // path from /connect folder becuase this is where program is executed
            File f = new File("./sampledata/actors.txt");
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String data = s.nextLine();
                String[] p = data.split(",");
                String date = Helper.convertDate(p[3]);
                Actor a = new Actor(p[0], Double.parseDouble(p[1]), p[2], date);
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        //Contracts info
        try {
            // path from /connect folder becuase this is where program is executed
            File f = new File("./sampledata/contracts.txt");
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String data = s.nextLine();
                String[] p = data.split(",");
                Actor.createContract(p[0], p[1], p[2], Integer.parseInt(p[3]), Double.parseDouble(p[4]));
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // To store daily balance/stock price from 2013-03-01 to 2013-03-16
    public static void setStartDate() {
        Debug d = new Debug();
        d.setDate("2013-03-16");
    }

    public static void dropTables() {
        String[] tableList = {"Actors", "Customers", "MarketAccounts", 
        "System", "Contracts", "Managers", "DailyBalance", "Stocks", "DailyStock", "Transactions"};

        for (int i = 0; i < tableList.length; i++) {
            String table = tableList[i];

            String deleteSql = "DROP TABLE IF EXISTS " + table;

            try (Connection conn = DriverManager.getConnection(Main.url);
                PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                
                pstmt.executeUpdate();

                conn.close();
                System.out.println("Deleted table " + table);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args[0].equals("run")) {
            UserInterface ui = new UserInterface();
        }
        else if (args[0].equals("setup")) {
            setup();
            insertSampleData();
            setStartDate();
        }
        else if (args[0].equals("reset")) {
            dropTables();
        }
    }
}