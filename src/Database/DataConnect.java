package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnect {

    private static final String URL = "jdbc:sqlite:Quiz.db";

    /** Creates a Connection object with the SQLite database
     *
     * @return a Connection object with the sqlite database.
     * @throws SQLException must be thrown when using java.sql.
     */
    public static Connection getConnection() throws SQLException {

        Connection conn = null;

        try{

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
            return conn;
        }
        catch(SQLException | ClassNotFoundException e) {

            System.err.println("Error " + e);
        }
        return null;
    }
}