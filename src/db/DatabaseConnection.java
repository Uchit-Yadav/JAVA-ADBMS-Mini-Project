package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/student_exam_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Shiva";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            System.out.println("Please add mysql-connector-java JAR to your classpath.");
            throw new SQLException("Driver not found", e);
        }
    }

    public static void testConnection() {
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database connection successful!");
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            System.out.println("Error: " + e.getMessage());
            System.out.println("\nPlease check:");
            System.out.println("1. MySQL server is running");
            System.out.println("2. Database 'student_exam_db' exists");
            System.out.println("3. Username and password are correct");
        }
    }
}
