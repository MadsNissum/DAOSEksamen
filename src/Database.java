import java.sql.*;

public class Database {
    public static Connection getConnection(String password) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=EksamensDatabase;encrypt=true;trustServerCertificate=true;","sa",password);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
