package demo.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class dbController {
    // database string used to connect to the database
    private static String url = "jdbc:h2:file:C:/Github/qforce/src/main/java/demo/data/demodb";

    // function for making the actual connection to the database
    public static Connection getConnection(){
        Connection conn = null;
        // Try to make a connection
        try {
            conn = DriverManager.getConnection(url, "admin", "admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void insertRecord(String URL) throws SQLException {
        // try to establish a connection
        try (Connection connection = getConnection();
             // create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO USAGE" +
                     "  (URL) VALUES " +
                     " (?);")) {
            preparedStatement.setString(1, URL);

            // execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}