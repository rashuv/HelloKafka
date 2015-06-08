package com.spnotes.kafka;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Simple Java Program to connect Oracle database by using Oracle JDBC thin driver Make sure you have Oracle JDBC thin
 * driver in your classpath before running this program
 * @author
 */
public class DBConnection {

    private static DBConnection dbConnInstance;
    private static Connection connection;

    private DBConnection() {
        getDBConnection();
    }

    private static void getDBConnection() {
        // URL of Oracle database server
        String url = "";

        // properties for creating connection to Oracle database
        Properties props = new Properties();
        props.setProperty("user", "");
        props.setProperty("password", "");

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection(url, props);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if (dbConnInstance == null) {
            dbConnInstance = new DBConnection();
        }
        return dbConnInstance;
    }

    public static Connection getConnection() {
        if (connection == null) {
            getDBConnection();
        }
        return connection;
    }

    public static void main(String args[]) throws SQLException, ClassNotFoundException {

        Connection conn = DBConnection.getInstance().getConnection();

        String sql = "";
        PreparedStatement preStatement = conn.prepareStatement(sql);
        ResultSet rs = preStatement.executeQuery();
        while (rs.next()) {
            System.out.println("EventId : " + rs.getString("EVENT_OID") + " || EventType : "
                    + rs.getString("EVENT_TYPE"));
        }
        System.out.println("Rows fetched");

    }
}
