package Objects;

import Database.DataConnect;
import Database.DataUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

    /**
     * Class creates a student object.
     * This is important because FXML tables use objects as their data.
     * As well as using objects instead of corresponding individual values is a much more organized way to handle data.
     */
public class Student {
    private StringProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty score;
    private StringProperty date;

    /**
     * Getter and setter methods for the Student object.
     */
    public void setId(String id) {
        this.id = new SimpleStringProperty(id);
    }
    public String getId(){
        return id.get();
    }
    public StringProperty idProperty() {
        return id;
    }
    public void setFirst(String first){
        this.firstName = new SimpleStringProperty(first);
    }
    public String getFirst(){
        return firstName.get();
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }
    public void setLast(String last){
        this.lastName = new SimpleStringProperty(last);
    }
    public String getLast(){
        return lastName.get();
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }
    public void setScore(String score) {
        this.score = new SimpleStringProperty(score);
    }
    public String getScore() {
        return score.get();
    }
    public StringProperty scoreProperty() {
        return score;
    }
    public void setDate(String date){
        this.date = new SimpleStringProperty(date);
    }
    public String getDate(){
        return date.get();
    }
    public StringProperty dateProperty() {
        return date;
    }

    /**
     * Various constructor methods due to different tables only needing certain properties.
     */
    public Student(String id, String first, String last, String score, String date){

        this.id = new SimpleStringProperty(id);
        this.firstName = new SimpleStringProperty(first);
        this.lastName = new SimpleStringProperty(last);
        this.score = new SimpleStringProperty(score);
        this.date = new SimpleStringProperty(date);
    }
    public Student(String id, String first, String last){

        this.id = new SimpleStringProperty(id);
        this.firstName = new SimpleStringProperty(first);
        this.lastName = new SimpleStringProperty(last);
    }

    /**
     * Method automatically generates a users ID.
     * ID is created by the combination of user's first name, last name, and 3 random digits.
     *
     * @param First
     * @param Last
     * @return
     */
    public static String GenerateID(String First, String Last) {

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        Random rand = new Random();
        Boolean control = true;
        String result = null;

        String first = First.toLowerCase();
        String last = Last.toLowerCase();

        int n = rand.nextInt(8);
        n = n + 1;
        int n2 = rand.nextInt(8);
        n2 = n2 + 1;
        int n3 = rand.nextInt(8);
        n3 = n3 + 1;

        String ID = first + "." + last + n + n2 + n3;

        if (check(ID)) return ID;
        else return GenerateID(first, last);
    }



        /**
         * Method that checks to ascertain that the new user's ID is not already taken.
         * If this is so, the user gets a new ID and will be checked again, until their ID is not taken.
         *
         * @param ID
         * @return
         */
    private static Boolean check(String ID) {

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {

            connection = DataConnect.getConnection();
            assert connection != null;
            statement = connection.createStatement();

            String sql = "Select * FROM Persons WHERE id='" + ID + "';";

            rs = statement.executeQuery(sql);
            return false;

        } catch (SQLException e) {

            return true;
        } finally {

            DataUtil.close(rs);
            DataUtil.close(statement);
            DataUtil.close(connection);
        }
    }
}