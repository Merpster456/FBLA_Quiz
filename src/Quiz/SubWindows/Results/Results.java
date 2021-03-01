package Quiz.SubWindows.Results;

import Database.DataConnect;
import Database.DataUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static Quiz.QuizController.*;
import static javafx.scene.paint.Color.WHITE;

public class Results implements Initializable {

    @FXML protected VBox quizBox;
    @FXML protected Label score;
    @FXML protected Pane pane;

    private Connection connection;
    private Statement statement;
    private ResultSet rs = null;

    public void initialize(URL url, ResourceBundle rb) {
        iterate();
        score();
        exitPrintButton();
    }

    protected void score() {
        int count = 0;
        if (q1.getCorrect()) count++;
        if (q2.getCorrect()) count++;
        if (q3.getCorrect()) count++;
        if (q4.getCorrect()) count++;
        if (q5.getCorrect()) count++;
        score.setText(count + "/5");
    }

    private void iterate() {

        for (int i = 1; i < 6; i++){

            int type;
            int randInt;
            boolean isCorrect;

            switch(i) {
                case 1 -> {
                    type = q1.getType();
                    randInt = q1.getRandInt();
                    isCorrect = q1.getCorrect();
                    getQuestions(type, randInt, isCorrect);
                } case 2 -> {
                    type = q2.getType();
                    randInt = q2.getRandInt();
                    isCorrect = q2.getCorrect();
                    getQuestions(type, randInt, isCorrect);
                } case 3 -> {
                    type = q3.getType();
                    randInt = q3.getRandInt();
                    isCorrect = q3.getCorrect();
                    getQuestions(type, randInt, isCorrect);
                } case 4 -> {
                    type = q4.getType();
                    randInt = q4.getRandInt();
                    isCorrect = q4.getCorrect();
                    getQuestions(type, randInt, isCorrect);
                } case 5 -> {
                    type = q5.getType();
                    randInt = q5.getRandInt();
                    isCorrect = q5.getCorrect();
                    getQuestions(type, randInt, isCorrect);
                }
            }
        }
    }
    private void getQuestions(int type, int randInt, boolean isCorrect) {
        try{
            connection = DataConnect.getConnection();
            statement = connection.createStatement();
            String SQL = "SELECT question, choices, answer FROM Questions WHERE type =" + type + " AND random =" + randInt + ";";
            rs = statement.executeQuery(SQL);

            String question = rs.getString(1);
            String choices = rs.getString(2);
            String answer = rs.getString(3);

            System.out.println(isCorrect);
            switch (type){
                case 1 -> {
                    setMultChoice(question, choices, answer, isCorrect);
                } case 2 -> {
                    setTF(question, answer, isCorrect);
                } case 3 -> {
                    setFill(question, answer, isCorrect);
                } case 4 -> {
                    setDrop(question, choices, answer, isCorrect);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
            System.err.println(e.getStackTrace()[0].getLineNumber());
        } finally {
            if (rs != null) DataUtil.close(rs);
            DataUtil.close(statement);
            DataUtil.close(connection);
        }
    }
    private void setMultChoice(String question, String dirtyChoices, String answer, boolean isCorrect){
        String[] choices = dirtyChoices.split(",");
        List<String> listchoices =  new ArrayList<String>(Arrays.asList(choices));
        int value;
        StackPane stack = new StackPane();
        Random random = new Random();
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(900);
        rectangle.setArcHeight(50);
        rectangle.setArcWidth(50);
        rectangle.setFill(WHITE);
        rectangle.setLayoutX(510);
        VBox innerBox = new VBox();
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        HBox hBox3 = new HBox();
        HBox hBox4 = new HBox();
        hBox1.setSpacing(20);
        hBox2.setSpacing(20);
        hBox3.setSpacing(20);
        hBox4.setSpacing(20);
        Label l1 = new Label();
        Label l2 = new Label();
        Label l3 = new Label();
        Label l4 = new Label();
        l1.setText("1.");
        l1.getStyleClass().add("nums");
        l2.setText("2.");
        l2.getStyleClass().add("nums");
        l3.setText("3.");
        l3.getStyleClass().add("nums");
        l4.setText("4.");
        l4.getStyleClass().add("nums");
        innerBox.prefWidth(900);
        /*
        Insets(Top, Right, Bottom, Left)
         */
        innerBox.setPadding(new Insets(30,20,20,40));
        innerBox.setSpacing(50);

        Label qLabel = new Label();
        qLabel.setText(question);
        qLabel.setAlignment(Pos.TOP_CENTER);
        qLabel.getStyleClass().add("question");
        qLabel.setWrapText(true);
        VBox choiceBox = new VBox();
        choiceBox.setSpacing(20);
        // Creates the answer choices for multiple choice question.
        ToggleButton choice1 = new ToggleButton();
        value = random.nextInt(4);
        choice1.setText(listchoices.get(value));
        listchoices.remove(value);
        choice1.getStyleClass().add("toggleButtons");
        hBox1.getChildren().add(l1);
        hBox1.getChildren().add(choice1);
        ToggleButton choice2 = new ToggleButton();
        value = random.nextInt(3);
        choice2.setText(listchoices.get(value));
        listchoices.remove(value);
        choice2.getStyleClass().add("toggleButtons");
        hBox2.getChildren().add(l2);
        hBox2.getChildren().add(choice2);
        ToggleButton choice3 = new ToggleButton();
        value = random.nextInt(2);
        choice3.setText(listchoices.get(value));
        listchoices.remove(value);
        choice3.getStyleClass().add("toggleButtons");
        hBox3.getChildren().add(l3);
        hBox3.getChildren().add(choice3);
        ToggleButton choice4 = new ToggleButton();
        choice4.setText(listchoices.get(0));
        choice4.getStyleClass().add("toggleButtons");
        hBox4.getChildren().add(l4);
        hBox4.getChildren().add(choice4);
        // Puts all choices in the same group.
        ToggleGroup group = new ToggleGroup();
        choice1.setToggleGroup(group);
        choice2.setToggleGroup(group);
        choice3.setToggleGroup(group);
        choice4.setToggleGroup(group);
        // Inserts the choices into the question bubble.
        choiceBox.getChildren().add(hBox1);
        choiceBox.getChildren().add(hBox2);
        choiceBox.getChildren().add(hBox3);
        choiceBox.getChildren().add(hBox4);
        innerBox.getChildren().add(qLabel);
        innerBox.getChildren().add(choiceBox);
        rectangle.setHeight(600);
        if (isCorrect) rectangle.getStyleClass().add("correct");
        else {
            rectangle.getStyleClass().add("wrong");
            Label correction = new Label("The correct answer was: " + answer);
            correction.getStyleClass().add("correction");
            innerBox.getChildren().add(correction);
            rectangle.setHeight(700);
        }
        stack.getChildren().add(rectangle);
        stack.getChildren().add(innerBox);
        quizBox.getChildren().add(stack);
    }
    public void setTF(String question, String answer, boolean isCorrect) {
        VBox innerBox = new VBox();
        StackPane stack = new StackPane();
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(900);
        rectangle.setArcHeight(50);
        rectangle.setArcWidth(50);
        rectangle.setFill(WHITE);
        rectangle.setLayoutX(510);
        innerBox.prefWidth(900);
        //Insets(Top, Right, Bottom, Left)
        innerBox.setPadding(new Insets(30,20,20,40));
        innerBox.setSpacing(50);
        Label qLabel = new Label();
        qLabel.setText(question);
        qLabel.setAlignment(Pos.TOP_CENTER);
        qLabel.getStyleClass().add("question");
        qLabel.setWrapText(true);
        VBox choiceBox = new VBox();
        choiceBox.setSpacing(20);
        ToggleButton trueButton = new ToggleButton();
        trueButton.setText("True");
        trueButton.getStyleClass().add("toggleButtons");
        ToggleButton falseButton = new ToggleButton();
        falseButton.setText("False");
        falseButton.getStyleClass().add("toggleButtons");
        ToggleGroup group = new ToggleGroup();
        trueButton.setToggleGroup(group);
        falseButton.setToggleGroup(group);
        choiceBox.getChildren().add(trueButton);
        choiceBox.getChildren().add(falseButton);
        innerBox.getChildren().add(qLabel);
        innerBox.getChildren().add(choiceBox);
        rectangle.setHeight(300);
        if (isCorrect) rectangle.getStyleClass().add("correct");
        else {
            rectangle.getStyleClass().add("wrong");
            if (answer.equals("0")) answer = "False";
            else answer = "True";
            Label correction = new Label("The correct answer was: " + answer);
            correction.getStyleClass().add("correction");
            innerBox.getChildren().add(correction);
            rectangle.setHeight(400);
        }
        stack.getChildren().add(rectangle);
        stack.getChildren().add(innerBox);
        quizBox.getChildren().add(stack);
    }
    private void setFill(String question, String answer, boolean isCorrect) {
        VBox innerBox = new VBox();
        StackPane stack = new StackPane();
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(900);
        rectangle.setArcHeight(50);
        rectangle.setArcWidth(50);
        rectangle.setFill(WHITE);
        innerBox.prefWidth(900);
        //Insets(Top, Right, Bottom, Left)
        innerBox.setPadding(new Insets(30,20,20,40));
        innerBox.setSpacing(50);
        VBox qBox = new VBox();
        qBox.setSpacing(10);
        Label warning = new Label("** If you believe the answer is numeric, use the number. Do not use letters to spell out the number.");
        warning.getStyleClass().add("warning");
        Label qLabel = new Label();
        qLabel.setText(question);
        qLabel.setAlignment(Pos.TOP_CENTER);
        qLabel.getStyleClass().add("question");
        qLabel.setWrapText(true);
        qBox.getChildren().addAll(qLabel, warning);
        TextField fill = new TextField();
        fill.setPrefHeight(25);
        fill.setPrefWidth(100);
        fill.setMaxHeight(25);
        fill.setMaxWidth(200);
        Label fillLabel = new Label();
        fillLabel.getStyleClass().add("secondText");
        fillLabel.setText("Fill in the blank:");
        HBox fillBox = new HBox();
        fillBox.setSpacing(10);
        fillBox.getChildren().addAll(fillLabel, fill);
        innerBox.getChildren().addAll(qBox, fillBox);
        rectangle.setHeight(300);
        if (isCorrect) rectangle.getStyleClass().add("correct");
        else {
            rectangle.getStyleClass().add("wrong");
            Label correction = new Label("The correct answer was: " + answer);
            correction.getStyleClass().add("correction");
            innerBox.getChildren().add(correction);
            rectangle.setHeight(400);
        }
        stack.getChildren().add(rectangle);
        stack.getChildren().add(innerBox);
        quizBox.getChildren().add(stack);
    }
    private void setDrop(String question, String dirtyChoices, String answer, boolean isCorrect){
        VBox innerBox = new VBox();
        StackPane stack = new StackPane();
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(900);
        rectangle.setArcHeight(50);
        rectangle.setArcWidth(50);
        rectangle.setFill(WHITE);
        innerBox.prefWidth(900);
        //Insets(Top, Right, Bottom, Left)
        innerBox.setPadding(new Insets(30,20,20,40));
        innerBox.setSpacing(50);
        Label qLabel = new Label();
        qLabel.setText(question);
        qLabel.setAlignment(Pos.TOP_CENTER);
        qLabel.getStyleClass().add("question");
        qLabel.setWrapText(true);
        ChoiceBox<String> drop = new ChoiceBox<String>();
        String[] choices = dirtyChoices.split(",");
        for (String choice:choices) {
            drop.getItems().add(choice);
        }
        Label dropLabel = new Label();
        dropLabel.getStyleClass().add("secondText");
        dropLabel.setText("Choose the best answer:");
        HBox dropBox = new HBox();
        dropBox.setSpacing(10);
        dropBox.getChildren().addAll(dropLabel, drop);
        innerBox.getChildren().addAll(qLabel, dropBox);
        rectangle.setHeight(300);
        if (isCorrect) rectangle.getStyleClass().add("correct");
        else {
            rectangle.getStyleClass().add("wrong");
            Label correction = new Label("The correct answer was: " + answer);
            correction.getStyleClass().add("correction");
            innerBox.getChildren().add(correction);
            rectangle.setHeight(400);
        }
        stack.getChildren().add(rectangle);
        stack.getChildren().add(innerBox);
        quizBox.getChildren().add(stack);
    }
    private void exitPrintButton() {
        HBox hBox = new HBox();
        hBox.setSpacing(50);
        Button exit = new Button("Exit");
        exit.getStyleClass().add("richBlue");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) quizBox.getScene().getWindow();
                stage.close();
            }
        });

        Button print = new Button("Print");
        print.getStyleClass().add("richBlue");
        print.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) quizBox.getScene().getWindow();

                Printer printer = Printer.getDefaultPrinter();
                PrinterJob printerJob = PrinterJob.createPrinterJob();

                PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
                printerJob.getJobSettings().setPageLayout(pageLayout);

                final double scaleX = pageLayout.getPrintableWidth() / pane.getBoundsInParent().getWidth();
                final double scaleY = pageLayout.getPrintableHeight() / pane.getBoundsInParent().getHeight();
                Scale scale = new Scale(scaleX, scaleY);
                pane.getTransforms().add(scale);

                if(printerJob.showPrintDialog(stage.getOwner()) && printerJob.printPage(pane)){
                    pane.getTransforms().remove(scale);
                    printerJob.endJob();
                }
                else {
                    pane.getTransforms().remove(scale);
                }
            }
        });
        hBox.getChildren().addAll(exit, print);
        quizBox.getChildren().add(hBox);
    }
}
