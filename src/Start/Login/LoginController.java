package Start.Login;

import Database.DataConnect;
import Database.DataUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static Start.StartController.isGuest;
import static javafx.scene.paint.Color.CRIMSON;

public class LoginController implements Initializable {

    @FXML private GridPane grid;
    @FXML private Button button;
    @FXML private TextField textField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorMessage;
    @FXML private Label topLabel;

    private Connection connection;
    private Statement statement;

    public static String id;

    /**
     * Method that initializes page.
     * Checks that program is connected to sqlite database.
     *
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {

        try {
            connection = DataConnect.getConnection();

            if (connection == null) {
                errorMessage.setText("Not Connected!");
                errorMessage.setTextFill(CRIMSON);
                errorMessage.setStyle("-fx-font-weight: bold;");
            } else {
                errorMessage.setText("Database Connected!");
                errorMessage.setTextFill(Color.WHITE);
                errorMessage.setStyle("-fx-font-weight: bold;");
            }
        } catch (SQLException e) {
            System.err.println(e.getStackTrace()[0].getLineNumber());
            System.out.println("Error: " + e);
        } finally {
            DataUtil.close(connection);
        }

        grid.setStyle("-fx-background-color: #3d5878");
        topLabel.setTextFill(Color.WHITE);
    }

    /**
     * Method that retrieves the value of the login fields, then calls the login function.
     *
     * @param event
     */
    @FXML
    protected void reactToClick(ActionEvent event) {


        String id = this.textField.getText();
        String password = this.passwordField.getText();
        findUser(id, password);
    }

    /**
     * Method that verifies whether the given values correspond with a user.
     * If so, it logs into the user's respective interface.
     * If not, a error message is raised alerting the user that either the username or password was incorrect.
     *
     * @param id
     * @param password
     */
    private void findUser(String id, String password) {

        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE id = '" + id + "' and pass = '" + password + "';";
        LoginController.id = id;

        try {

            connection = DataConnect.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            int isAdvisor = rs.getInt(5);

            DataUtil.close(rs);
            DataUtil.close(statement);
            DataUtil.close(connection);

            try {

                // Checks whether the user is an advisor or not, and calls respective functions.
                if (isAdvisor == 1) advisorLogin();
                else if (isAdvisor == 0) studentLogin();

            } catch (IOException e) {

                System.err.println("Error: " + e);
                errorMessage.setText("Error!");
                errorMessage.setTextFill(CRIMSON);
            }
        } catch (SQLException e) {

            System.err.println(e.getStackTrace()[0].getLineNumber());
            System.out.println("Error: " + e);
            errorMessage.setText("Wrong User or Pass");
            errorMessage.setTextFill(CRIMSON);
        } finally {

            if (rs != null) DataUtil.close(rs);
            DataUtil.close(statement);
            DataUtil.close(connection);
        }
    }

    /**
     * Method that logs into the advisor interface.
     * Opens AdvisorUI.fxml.
     *
     * @throws IOException
     */
    private void advisorLogin() throws IOException {

        Stage stage = (Stage) button.getScene().getWindow();

        stage.close();
        Stage nextStage = new Stage();
        Pane root = FXMLLoader.load(getClass().getResource("/AdvisorInterface/AdvisorUI.fxml"));
        Scene scene = new Scene(root);
        nextStage.setResizable(false);
        nextStage.setTitle("Advisor");
        nextStage.setScene(scene);
        nextStage.show();
    }

    /**
     * Method that logs into the Quiz as the corresponding user.
     * Opens Quiz.fxml.
     *
     * @throws IOException
     */
    private void studentLogin() throws IOException {

        isGuest = false; // Since the user logged into their account, they are not a guest.
        Stage stage = (Stage) button.getScene().getWindow();

        Stage nextStage = new Stage();
        Pane root = FXMLLoader.load(getClass().getResource("/Quiz/Quiz.fxml"));
        Scene scene = new Scene(root);
        nextStage.setResizable(false);
        nextStage.setTitle("Quiz");
        nextStage.setScene(scene);
        nextStage.show();

        stage.close();
    }
}