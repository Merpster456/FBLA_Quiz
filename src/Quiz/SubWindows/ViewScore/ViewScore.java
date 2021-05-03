package Quiz.SubWindows.ViewScore;

import Database.DataConnect;
import Database.DataUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static Quiz.QuizController.*;
import static Start.Login.LoginController.id;

public class ViewScore implements Initializable {

    @FXML Button button;

    private Connection connection;

    private int mc = 0;
    private int tf = 0;
    private int fill = 0;
    private int drop = 0;

    /**
     * Method that initializes the page.
     * Calls the function recordScore().
     *
     * @param url needed for initialization of the page.
     * @param rb needed for initialization of the page.
     */
    public void initialize(URL url, ResourceBundle rb) {
        recordScore();
    }

    /**
     * Method that records not only the score the user got on the quiz, but also keeps
     * track of what type of question the user got wrong.
     */
    private void recordScore() {
        try {
           switch (q1.getType()){
               case 1 -> {
                   if (!q1.getCorrect()) mc++;
               } case 2 -> {
                   if (!q1.getCorrect()) tf++;
               } case 3 -> {
                   if (!q1.getCorrect()) fill++;
               } case 4 -> {
                   if (!q1.getCorrect()) drop++;
               }
           }
           switch (q2.getType()){
                case 1 -> {
                    if (!q2.getCorrect()) mc++;
                } case 2 -> {
                    if (!q2.getCorrect()) tf++;
                } case 3 -> {
                    if (!q2.getCorrect()) fill++;
                } case 4 -> {
                    if (!q2.getCorrect()) drop++;
                }
            }
            switch (q3.getType()){
                case 1 -> {
                    if (!q3.getCorrect()) mc++;
                } case 2 -> {
                    if (!q3.getCorrect()) tf++;
                } case 3 -> {
                    if (!q3.getCorrect()) fill++;
                } case 4 -> {
                    if (!q3.getCorrect()) drop++;
                }
            }
            switch (q4.getType()){
                case 1 -> {
                    if (!q4.getCorrect()) mc++;
                } case 2 -> {
                    if (!q4.getCorrect()) tf++;
                } case 3 -> {
                    if (!q4.getCorrect()) fill++;
                } case 4 -> {
                    if (!q4.getCorrect()) drop++;
                }
            }
            switch (q5.getType()){
                case 1 -> {
                    if (!q5.getCorrect()) mc++;
                } case 2 -> {
                    if (!q5.getCorrect()) tf++;
                } case 3 -> {
                    if (!q5.getCorrect()) fill++;
                } case 4 -> {
                    if (!q5.getCorrect()) drop++;
                }
            }

            // Time stamps when the response was submitted.
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate localDate = LocalDate.now();
            String date = localDate.format(dateFormatter);

            String SQL = "INSERT INTO Responses VALUES(?,?,?,?,?,?);";
            connection = DataConnect.getConnection();
            PreparedStatement insert = connection.prepareStatement(SQL);

            if (id == null) {
                id = "Guest";
            }

            insert.setString(1, id);
            insert.setInt(2, mc);
            insert.setInt(3, tf);
            insert.setInt(4, fill);
            insert.setInt(5, drop);
            insert.setString(6, date);
            insert.executeUpdate();

            DataUtil.close(insert);
            DataUtil.close(connection);
        }
        catch(SQLException e){
                System.out.println("Error: " + e);
                System.err.println(e.getStackTrace()[0].getLineNumber());
        }
    }

    /**
     * Method that adds functionality to the "View Score" button.
     * Opens Results.fxml.
     *
     * @param event the button being clicked.
     * @throws IOException Needs to be thrown when dealing with fxml objects.
     */
    @FXML
    protected void results(ActionEvent event) throws IOException {

        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();

        Stage nextStage = new Stage();
        Pane root = FXMLLoader.load(getClass().getResource("/Quiz/SubWindows/Results/Results.fxml"));
        Scene scene = new Scene(root);
        nextStage.setResizable(false);
        nextStage.setScene(scene);
        nextStage.show();
    }
}