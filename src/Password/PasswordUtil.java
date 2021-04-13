package Password;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
     * @param preparedPass password with salt and pepper.
     * @return a SHA-512 hash to be stored in the database.
     */
    public static String hash(String preparedPass) {

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
     *
     * @param username to look up salt and hash in database
     * @return true or false that will represent whether the password and username is correct, or not.
     */
    public static boolean checkPass(String username, String pass) {

    }
    public static String getSalt(String username) {
        String sql = "SELECT FROM Users WHERE ("
    }
}
