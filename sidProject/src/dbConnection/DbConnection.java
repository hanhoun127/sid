package dbConnection;
import java.sql.*;

public class DbConnection {
    private static Connection con;
    private static String URL="jdbc:mysql://localhost:3306/pharmacie";
    private static String USER="root";
    private static String PASSWORD="";
    // methode to etablish connection to database
    public static Connection connect() throws SQLException {
    
    try {
        // Charger le pilote JDBC
			Class.forName("com.mysql.cj.jdbc.Driver"); 
        // Établir une connexion
			con= DriverManager.getConnection(URL,USER,PASSWORD);
		}catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Error loading JDBC driver", e);
        }
    return con;
    }
}
