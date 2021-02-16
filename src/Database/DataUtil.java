package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Closes all SQL connections
public class DataUtil {

    public static void close(Connection connection){
        if (connection != null){
            try{
                connection.close();
            } catch (SQLException e){
                System.out.println("Error: " + e);
                System.err.println(e.getStackTrace()[0].getLineNumber());
            }
        }
    }
    public static void close(Statement statement){
        if (statement != null){
            try{
                statement.close();
            } catch (SQLException e){
                System.out.println("Error: " + e);
                System.err.println(e.getStackTrace()[0].getLineNumber());
            }
        }
    }
    public static void close(ResultSet resultSet){
        if (resultSet != null){
            try{
                resultSet.close();
            } catch (SQLException e){
                System.out.println("Error: " + e);
                System.err.println(e.getStackTrace()[0].getLineNumber());
            }
        }
    }
}