package Start.Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static javafx.scene.paint.Color.CRIMSON;
import static javafx.scene.paint.Color.GREEN;

import java.io.IOException;
import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;

import Database.DataConnect;
import Database.DataUtil;

import static Start.StartController.isGuest;

public class LoginController implements Initializable {

    @FXML private GridPane grid;
    @FXML private Button button;
    @FXML private TextField textField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorMessage;
    @FXML private Label topLabel;
    @FXML private Label idLabel;
    @FXML private Label passLabel;

    private Connection connection;
    private Statement statement;

    public static String id;

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
     * Gets value of the login fields,
     * then calls the login function
     *
     * @param event
     * @throws Exception
     */
    @FXML
    protected void reactToClick(ActionEvent event) throws Exception {


        String id = (String) this.textField.getText();
        String password = (String) this.passwordField.getText();
        findUser(id, password);
    }

    /**
     * Checks for user with given
     * credentials then logs into
     * respective interfaces
     *
     * @param id
     * @param password
     * @throws IOException
     */
    private void findUser(String id, String password) throws IOException {

        ResultSet rs = null;

        String sql = "SELECT * FROM users WHERE id = '" + id + "' and pass = '" + password + "';";
        LoginController.id = id;

        try {

            connection = DataConnect.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            System.out.print(rs.getString(3));
            String advisor = rs.getString(3);

            try {

                if (advisor.equals("1")) advisorLogin();
                else if (advisor.equals("0")) studentLogin();
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
     * Logs into advisor interface
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
        nextStage.setTitle("AdvisorUI");
        nextStage.setScene(scene);
        nextStage.show();
    }

    private void studentLogin() throws IOException {

        isGuest = false;
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