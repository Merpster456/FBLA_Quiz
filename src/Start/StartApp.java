package Start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Starts the application.
 */
public class StartApp extends Application {

    /**
     * Method that starts the application by opening Start.fxml.
     *
     * @param primaryStage is fetched by the launch method with Javafx.application.Application.
     * @throws Exception needs to be thrown to begin the application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Start.fxml"));
        primaryStage.setTitle("Start");
        primaryStage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Main method that starts the program.
     * Calls the launch method within the javaFX Application class.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}