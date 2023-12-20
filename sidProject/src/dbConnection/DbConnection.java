package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Connection con;
    private static String URL = "jdbc:mysql://localhost:3306/pharmacie";
    private static String USER = "root";
    private static String PASSWORD = "";

    // method to establish connection to the database
    public static Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            con.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Error loading JDBC driver", e);
        }
        return con;
    }
}
