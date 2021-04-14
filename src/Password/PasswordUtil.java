package Password;

import Database.DataConnect;
import Database.DataUtil;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * It is well known that storing plaintext passwords inside a database is a horrendous idea.
 * Therefore I add a little hash alongside some salt and pepper to make the programs password management
 * better than most modern day websites!
 *
 * I will include a section on my password management alongside what is hash, salt, and pepper in my README file.
 */
public class PasswordUtil {

    private static final String pepper = "lUH<mXX5@Rqg"; // Pepper is a constant added to every password before it's hashed.

    /**
     * Method that generates a random 12 character alphanumeric string that will be used as salt for each password.
     * Each user's salt is unique and is stored within the database next to the user's hash.
     *
     * @return salt string.
     */
    public static String generateSalt() {
        final String alphaNumeric = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*-_=+[{]}" +
                "|';?.>,<`~";
        final int saltLength = 12;

        StringBuilder salt = new StringBuilder();

        for (int i = 0; i < saltLength; i++) {

            int randInt = new Random().nextInt(alphaNumeric.length());
            char randChar = alphaNumeric.charAt(randInt);
            salt.append(randChar);
        }
        return salt.toString();
    }

    /**
     * This method turns the salt and peppered password into a SHA-512 hash so it is
     * safe to be stored within the database.
     *
     * @param pass the user's password.
     * @param salt the user's generated salt.
     * @return a SHA-512 hash to be stored in the database.
     */
    public static String generateHash(String pass, String salt) {

        String preparedPass = pepper + pass + salt;
        MessageDigest digest = null;

        try{
            // There are multiple versions of SHA, SHA-512 gives the greatest amount of protection.
            digest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e){
            System.err.println("No Such Algorithm");
        }

        byte[] encodedHash = digest.digest(preparedPass.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder(2 * encodedHash.length);

        for (int i = 0; i < encodedHash.length; i++) {

            String hex = Integer.toHexString(0xff & encodedHash[i]);

            if (hex.length() == 1) {

                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Method that checks whether login input is correct.
     *
     * @param id to look up salt and hash in database
     * @return integer value that will represent whether the password and username is correct or not, and whether the
     * user is a student or advisor.
     *
     * 0 = Student
     * 1 = Advisor
     * 2 = Wrong username or password
     */
    public static int checkPass(String id, String pass) {

        String salt = getSalt(id);

        if (salt.equals("ER")) {

            return 2;
        } else {

            String hash = (generateHash(pass, salt));
            String sql = "SELECT advisor FROM Users WHERE id = '" + id + "' and hash = '" + hash +  "';";
            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;
            int isAdvisor;

            try {

                connection = DataConnect.getConnection();
                statement = connection.createStatement();
                rs = statement.executeQuery(sql);

                isAdvisor = rs.getInt(1);
                return isAdvisor;

            } catch (SQLException wrongPassword) {

                return 2;
            } finally {

                DataUtil.close(rs);
                DataUtil.close(statement);
                DataUtil.close(connection);
            }
        }
    }

    /**
     * Method that retrieves the salt of a specific user to prepare the password to hash.
     *
     * @param id the string input into the id text box by the user.
     * @return the user's salt value. If no user is found it will return "ER" for error.
     */
    public static String getSalt(String id) {

        String sql = "SELECT salt FROM Users WHERE id = '" + id + "';";
        ResultSet rs = null;
        Connection connection = null;
        Statement statement = null;
        String salt;

        try {

            connection = DataConnect.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);

            salt = rs.getString(1);
            return salt;
        } catch (SQLException wrongID) {

            return "ER";
        } finally {

            DataUtil.close(rs);
            DataUtil.close(statement);
            DataUtil.close(connection);
        }

    }
    /**
     * Method automatically generates a new user's password.
     * Password is created in combination of 4 zeros, and 4 random digits.
     *
     * @return
     * */
    public static String GeneratePass(){

        Random rand = new Random();

        int n = rand.nextInt(8);
        n = n + 1;
        int n2 = rand.nextInt(8);
        n2 = n2 + 1;
        int n3 = rand.nextInt(8);
        n3 = n3 + 1;
        int n4 = rand.nextInt(8);
        n4 = n4 +1;

        return "0000" + n + n2 + n3 + n4;
    }
}
