package OthelloApp.DBUtil;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBUtil {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/othello";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Student_1234";
    private static Connection connection;
    private static Statement statement;

    public static Statement getStatement() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            statement = connection.createStatement();
            return statement;
        } catch (SQLException e) {
            return null;
        }
    }

    public static void closeDbConnection() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Statement getStatementAutoCommitFalse() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            return statement;
        } catch (SQLException e) {
            return null;
        }
    }
}