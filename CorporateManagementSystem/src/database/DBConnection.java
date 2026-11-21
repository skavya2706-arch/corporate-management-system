    package database;

    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.SQLException;
    import java.sql.*;

    public class DBConnection {

        private static final String URL = "jdbc:mysql://127.0.0.1:3306/corporate_management_system";
        private static final String USERNAME = "root";
        private static final String PASSWORD = "";

        public static Connection getConnection() {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Database Connected Successfully!");
                return connection;

            } catch (Exception e) {

                System.out.println("Database Connection Failed!");
                e.printStackTrace();

            }

            return null;
        }
    }