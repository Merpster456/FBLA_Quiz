package Start;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    @FXML private Ellipse loginEllipse;
    @FXML private Ellipse guestEllipse;

    public static String primaryDark = "#3d5878";
    public static String primary = "#5377a1";
    public static String primaryLight = "#789cc7";

    public static boolean isGuest;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loginRefresh(null);
        guestRefresh(null);
    }

    @FXML
    protected void login(MouseEvent event) throws IOException {

        Stage stage = (Stage) loginEllipse.getScene().getWindow();

        stage.close();
        Stage nextStage = new Stage();
        Pane root = FXMLLoader.load(getClass().getResource("/Login/Login.FXML"));
        Scene scene = new Scene(root);
        nextStage.setResizable(false);
        nextStage.setTitle("Login");
        nextStage.setScene(scene);
        nextStage.show();
    }

    @FXML
    protected void guest(MouseEvent event) throws IOException {

        isGuest = true;

        Stage stage = (Stage) guestEllipse.getScene().getWindow();

        stage.close();
        Stage nextStage = new Stage();
        Pane root = FXMLLoader.load(getClass().getResource("/Quiz/Quiz.fxml"));
        Scene scene = new Scene(root);
        nextStage.setResizable(false);
        nextStage.setScene(scene);
        nextStage.show();
    }

    @FXML
    protected void setLogin(MouseEvent event) {

        loginEllipse.setStyle("-fx-fill: " + primaryLight + ";");
    }

    @FXML
    protected void loginRefresh(MouseEvent event) {

        loginEllipse.setStyle("-fx-fill: " + primary + ";");
    }

    @FXML
    protected void setGuest(MouseEvent event) {

        guestEllipse.setStyle("-fx-fill: #D8D8D8;");
    }

    @FXML
    protected void guestRefresh(MouseEvent event) {

        guestEllipse.setStyle("-fx-fill: #A8A8A8;");
    }
    @FXML
    protected void guestPressed(MouseEvent event) {
        guestEllipse.setStyle("-fx-fill: #686868;");
    }
    @FXML
    protected void loginPressed(MouseEvent event) {
        loginEllipse.setStyle("-fx-fill: " + primaryDark + ";");
    }
}
