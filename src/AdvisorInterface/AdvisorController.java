package AdvisorInterface;

import Database.DataConnect;
import Database.DataUtil;
import Objects.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static Start.Login.LoginController.id;

public class AdvisorController implements Initializable {

    @FXML private Label welcomeLabel;
    @FXML private TableView<Student> scoreTable;
    @FXML private TableColumn<Student, String> idCol;
    @FXML private TableColumn<Student, String> firstCol;
    @FXML private TableColumn<Student, String> lastCol;
    @FXML private TableColumn<Student, String> scoreCol;
    @FXML private TableColumn<Student, String> dateCol;

    private Connection connection;
    private Statement statement;

    public void initialize(URL url, ResourceBundle rb) {
        setWelcome();
        setTable();
    }
    private void setWelcome(){
        welcomeLabel.setText("Welcome " + id + "!");
    }
    @FXML
    private void setTable(){
        String SQL = "SELECT * FROM Responses LEFT JOIN Users ON Responses.id=Users.id ORDER BY julianday(date);";
        ResultSet rs = null;
        ObservableList<Student> students = FXCollections.observableArrayList();

        try {
            connection = DataConnect.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(SQL);

            while (rs.next()) {
                if (!students.isEmpty()) {
                    for (int i = 0; i < students.size(); i++) {
                        if ((rs.getString(1).equals(students.get(i).getId()) && rs.getString(6).equals(students.get(i).getDate())
                                && String.valueOf(5 - (rs.getInt(2) + rs.getInt(3) + rs.getInt(4) +
                                rs.getInt(5))).equals(students.get(i).getScore()))) {
                            break;
                        } else {
                            if (i + 1 == students.size()) {
                                students.add(new Student(rs.getString(1),
                                        rs.getString(9),
                                        rs.getString(10),
                                        String.valueOf(5 - (rs.getInt(2) + rs.getInt(3) + rs.getInt(4) + rs.getInt(5))),
                                        rs.getString(6)));
                                break;
                            }
                        }
                    }
                } else {
                    students.add(new Student(rs.getString(1),
                            rs.getString(9),
                            rs.getString(10),
                            String.valueOf(5 - (rs.getInt(2) + rs.getInt(3) + rs.getInt(4) + rs.getInt(5))),
                            rs.getString(6)));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
            System.err.println(e.getStackTrace()[0].getLineNumber());
        } finally {
            DataUtil.close(rs);
            DataUtil.close(statement);
            DataUtil.close(connection);
        }

        this.idCol.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
        this.firstCol.setCellValueFactory(new PropertyValueFactory<Student, String>("first"));
        this.lastCol.setCellValueFactory(new PropertyValueFactory<Student, String>("last"));
        this.scoreCol.setCellValueFactory(new PropertyValueFactory<Student, String>("score"));
        this.dateCol.setCellValueFactory(new PropertyValueFactory<Student, String>("date"));

        idCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastCol.setCellFactory(TextFieldTableCell.forTableColumn());
        scoreCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());

        scoreTable.setItems(null);
        scoreTable.setItems(students);
    }
    @FXML
    protected void manageUsers(ActionEvent event) throws IOException {
        Stage stage = (Stage) scoreTable.getScene().getWindow();
        stage.close();

        Stage nextStage = new Stage();
        Pane root = FXMLLoader.load(getClass().getResource("/AdvisorInterface/ManageUsers/ManageUsers.fxml"));
        Scene scene = new Scene(root);
        nextStage.setTitle("Manage Users");
        nextStage.setResizable(false);
        nextStage.setScene(scene);
        nextStage.show();
    }
    @FXML
    protected void print(ActionEvent event) {
        Stage stage = (Stage) scoreTable.getScene().getWindow();

        Printer printer = Printer.getDefaultPrinter();
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        printerJob.getJobSettings().setPageLayout(pageLayout);

        final double scaleX = pageLayout.getPrintableWidth() / scoreTable.getBoundsInParent().getWidth();
        final double scaleY = pageLayout.getPrintableHeight() / scoreTable.getBoundsInParent().getHeight();
        Scale scale = new Scale(scaleX, scaleY);
        scoreTable.getTransforms().add(scale);

        if(printerJob.showPrintDialog(stage.getOwner()) && printerJob.printPage(scoreTable)){
            scoreTable.getTransforms().remove(scale);
            printerJob.endJob();
        }
        else {
            scoreTable.getTransforms().remove(scale);
        }
    }
}
