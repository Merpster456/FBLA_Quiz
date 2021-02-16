package Database;
import java.sql.*;

public class DataConnect {

    private static final String URL = "jdbc:sqlite:Quiz.db";

    /** Creates a connection object with the SQLite database
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection()throws SQLException {

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