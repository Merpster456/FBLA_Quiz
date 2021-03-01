package AdvisorInterface.ManageUsers;

import Database.DataUtil;
import Objects.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Database.DataConnect;
import javafx.stage.StageStyle;

public class ManageUsers implements Initializable {

    @FXML private Button back;
    @FXML private ComboBox delCombo;
    @FXML private ComboBox changeCombo;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> idCol;
    @FXML private TableColumn<Student, String> firstCol;
    @FXML private TableColumn<Student, String> lastCol;
    @FXML private TextField newFirst;
    @FXML private TextField newLast;
    @FXML private TextField changeFirst;
    @FXML private TextField changeLast;
    @FXML private Label firstErr;
    @FXML private Label lastErr;
    @FXML private Label err;
    @FXML private Label delErr;
    @FXML private Label changeErr;

    private Connection connection;
    private Statement statement;

    @Override
    public void initialize(URL url, ResourceBundle rb){

        setTable();
        setBox();
    }

    private void setTable(){

        String SQL = "SELECT * FROM Users WHERE advisor = 0;";
        ResultSet rs = null;
        List<Student> list = new ArrayList<Student>();
        Student student = null;
        ObservableList<Student> students = FXCollections.observableArrayList();

        try {

            Connection connection = DataConnect.getConnection();
            assert connection != null;
            statement = connection.createStatement();
            rs = statement.executeQuery(SQL);

            while (rs.next()) {

                students.add(new Student(rs.getString(1),
                        rs.getString(3),
                        rs.getString(4)));
            }

            this.idCol.setCellValueFactory(new PropertyValueFactory<Student, String>("Id"));
            this.firstCol.setCellValueFactory(new PropertyValueFactory<Student, String>("First"));
            this.lastCol.setCellValueFactory(new PropertyValueFactory<Student, String>("Last"));

            idCol.setCellFactory(TextFieldTableCell.forTableColumn());
            firstCol.setCellFactory(TextFieldTableCell.forTableColumn());
            lastCol.setCellFactory(TextFieldTableCell.forTableColumn());

            studentTable.setItems(null);
            studentTable.setItems(students);

            setBox();
        } catch (SQLException e) {

            System.out.println("Error: " + e);
            System.err.println(e.getStackTrace()[0].getLineNumber());
        } finally {

            DataUtil.close(rs);
            DataUtil.close(statement);
            DataUtil.close(connection);
        }

    }

    @FXML
    protected void back(ActionEvent event) throws IOException {

        Stage stage = (Stage) back.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/AdvisorInterface/AdvisorUI.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    @FXML protected void backChange(MouseEvent event) { back.setStyle("-fx-text-fill: black"); }
    @FXML protected void refresh(MouseEvent event) {
        back.setStyle("-fx-text-fill: white");
    }

    @FXML
    protected void submit(ActionEvent event) throws IOException {

        ResultSet rs = null;
        boolean control = true;

        String first = (String) this.newFirst.getText();
        String last = (String) this.newLast.getText();

        firstErr.setText("");
        lastErr.setText("");
        err.setText("");

        if (first.length() < 1) {

            firstErr.setText("First Name is Required!");
            control = false;
            err.setText("Need to Fill Required Field!");
        }
        if (last.length() < 1) {

            lastErr.setText("Last Name is Required!");
            control = false;
            err.setText("Need to Fill Required Field!");
        }
       if (control) {

            try {

                String id = Student.GenerateID(first, last);
                String pass = Student.GeneratePass();

                String sql = "INSERT INTO Users VALUES ('" + id + "', '" +  pass +
                "', '" + first + "', '" +  last + "', 0);";
                try {

                    connection = DataConnect.getConnection();
                    statement = connection.createStatement();

                    statement.executeQuery(sql);

                } catch (SQLException ignore) {

                    setTable();
                    showCreds(id, pass);
                }
            } catch (Exception e){

                System.err.println("Error: " + e);
                err.setText("Error!");
            } finally {

                DataUtil.close(rs);
                DataUtil.close(statement);
                DataUtil.close(connection);
            }
        }
    }
    @FXML
    protected void cancel(ActionEvent event) throws IOException{

        newFirst.setText("");
        newLast.setText("");

        firstErr.setText("");
        lastErr.setText("");
        err.setText("");
    }
    private void showCreds(String id, String pass) {

        Stage newStage = new Stage();
        StackPane pane = new StackPane();
        GridPane gridPane = new GridPane();

        pane.getChildren().add(gridPane);
        gridPane.setStyle("-fx-background-color: " +Start.StartController.primary + ";");

        Label idLabel = new Label();
        Label passLabel = new Label();
        Button quit = new Button("Quit");

        quit.setOnAction(event -> newStage.close());

        idLabel.setText("Users ID: " + id);
        passLabel.setText("Users Password: " + pass);

        idLabel.setTextFill(Color.WHITE);
        passLabel.setTextFill(Color.WHITE);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(50,50,50,50));
        gridPane.setVgap(10.0);
        gridPane.add(idLabel, 0, 0);
        gridPane.add(passLabel, 0, 1);
        gridPane.add(quit, 0, 3);

        Scene scene = new Scene(pane, 500, 300);

        newStage.initStyle(StageStyle.UTILITY);
        newStage.setScene(scene);
        newStage.show();
    }

    private void setBox(){

        String sql = "SELECT id FROM Users WHERE advisor=0;";
        ResultSet rs = null;

        try{

            delCombo.getItems().clear();
            changeCombo.getItems().clear();

            delCombo.getItems().add("Select User");
            changeCombo.getItems().add("Select User");

            connection = DataConnect.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);

            while(rs.next()) {

                delCombo.getItems().add(rs.getString(1));
                changeCombo.getItems().add(rs.getString(1));
            }
        } catch (SQLException e){

            System.out.println("Error: " + e);
            System.err.println(e.getStackTrace()[0].getLineNumber());
        } finally {

            delCombo.setValue("Select User");
            changeCombo.setValue("Select User");

            DataUtil.close(rs);
            DataUtil.close(statement);
            DataUtil.close(connection);
        }
    }

    /**
     * Method to delete users from Persons table
     * @param event
     * @throws IOException
     */
    @FXML
    protected void Delete(ActionEvent event) throws IOException{

        String id = (String) delCombo.getValue();
        String sql = "SELECT * FROM Users WHERE id='" + id + "';";
        ResultSet rs = null;

        if (delCombo.getValue().equals("Select User")) {
            delErr.setText("Please Select User");
        } else {
            try {

                connection = DataConnect.getConnection();
                statement = connection.createStatement();
                rs = statement.executeQuery(sql);

                DataUtil.close(rs);
                DataUtil.close(statement);
                try {
                   sql = "DELETE FROM Users WHERE id='" + id + "';";
                   statement = connection.createStatement();
                   statement.executeUpdate(sql);
                   setTable();

                   DataUtil.close(statement);
                   try {
                       sql = "DELETE FROM Responses WHERE id='" + id + "';";
                       statement = connection.createStatement();
                       statement.executeUpdate(sql);
                   } catch (SQLException e) {
                       System.out.println("Error: " + e);
                       System.err.println(e.getStackTrace()[0].getLineNumber());
                   }

                } catch (SQLException e) {
                    System.out.println("Error: " + e);
                    System.err.println(e.getStackTrace()[0].getLineNumber());
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e);
                System.err.println(e.getStackTrace()[0].getLineNumber());
            } finally {
                DataUtil.close(rs);
                DataUtil.close(statement);
                DataUtil.close(connection);
            }
        }
    }

    /**
     * Method to change user data!
     * @param event
     */
    @FXML
    protected void Change(ActionEvent event) {

        String id = (String) changeCombo.getValue();
        String first = changeFirst.getText();
        String last = changeLast.getText();

        changeErr.setText("");

        changeFirst.setText("");
        changeLast.setText("");

        if (id.equals("Select User")) {
            changeErr.setText("Please Select User");
        } else {
            if (first.length() > 0) {

                String sql = "UPDATE Users SET firstName = '" + first + "' WHERE id = '" + id + "';";

                try {

                    connection = DataConnect.getConnection();
                    statement = connection.createStatement();

                    try {
                        statement.executeQuery(sql);
                    } catch (SQLException ignore) {}
                } catch (SQLException e) {

                    System.err.println(e.getStackTrace()[0].getLineNumber());
                    System.out.println("Error: " + e);
                } finally {

                    DataUtil.close(statement);
                    DataUtil.close(connection);
                }
            } if (last.length() > 0) {

                String sql = "UPDATE Users SET lastName = '" + last + "' WHERE id = '" + id + "';";

                try {

                    connection = DataConnect.getConnection();
                    statement = connection.createStatement();

                    try {
                        statement.executeQuery(sql);
                    } catch (SQLException ignore) {
                    }
                } catch (SQLException e) {

                    System.err.println(e.getStackTrace()[0].getLineNumber());
                    System.out.println("Error: " + e);
                }
            }
        }
        setTable();
        setBox();
    }

    @FXML
    protected void changeCancel(ActionEvent event) {

        changeCombo.getItems().clear();
        setBox();

        changeFirst.setText("");
        changeLast.setText("");

        changeErr.setText("");
    }
}