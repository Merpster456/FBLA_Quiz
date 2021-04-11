package Quiz;

import Database.DataConnect;
import Database.DataUtil;
import Objects.Question;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static javafx.scene.paint.Color.WHITE;


public class
QuizController implements Initializable {

    @FXML private VBox quizBox;

    private Connection connection;
    private Statement statement;
    private final Map<Integer, String> answers = new HashMap<Integer, String>();
    public static Question q1 = new Question();
    public static Question q2 = new Question();
    public static Question q3 = new Question();
    public static Question q4 = new Question();
    public static Question q5 = new Question();

    public void initialize(URL url, ResourceBundle rb) {
        getQuestions();
        setSubmit();
    }

    protected void getQuestions(){

        Random random = new Random();
        int value;
        int randInt;
        String SQL;
        ResultSet rs = null;
        List<Integer> typeList = new ArrayList<Integer>();
        /*
        Type 1 = Multiple Choice
        Type 2 = True False
        Type 3 = Fill in the Blank
        type 4 = Dropdown
         */
        typeList.add(1);
        typeList.add(2);
        typeList.add(3);
        typeList.add(4);
        typeList.add(5);
        int type;
        String question;
        String choices;
        String answer;
        int id = 1;
        Map<Integer, Integer> check = new HashMap<Integer, Integer>();
        while (typeList.size() > 0) {
            try {
                connection = DataConnect.getConnection();
                statement = connection.createStatement();
                ArrayList<String> questions = new ArrayList<String>();
                if (typeList.size() == 1) {
                    type = typeList.get(0);
                    typeList.remove(0);
                } else {
                    value = random.nextInt(typeList.size());
                    type = typeList.get(value);
                    typeList.remove(value);
                }
                randInt = random.nextInt(15);
                if (type == 5) {
                    type = random.nextInt(4) + 1;
                }
                if (!check.isEmpty()) {
                    if (check.containsKey(type)){
                        if (check.get(type) == randInt)
                            if (randInt < 14) randInt ++;
                            else randInt--;
                    }
                }
                SQL = "SELECT question, choices, answer FROM Questions WHERE type=" + type + " AND random=" + randInt + "";
                rs = statement.executeQuery(SQL);

                question = rs.getString(1);
                choices = rs.getString(2);
                answer = rs.getString(3);

                if (type == 3) {
                    answer = answer.toLowerCase();
                }

                answers.put(id, answer);
                check.put(type, randInt);

                switch (id) {
                    case 1 -> {
                        q1.setAnswer(answer);
                        q1.setType(type);
                        q1.setRandInt(randInt);
                    }
                    case 2 -> {
                        q2.setAnswer(answer);
                        q2.setType(type);
                        q2.setRandInt(randInt);
                    }
                    case 3 -> {
                        q3.setAnswer(answer);
                        q3.setType(type);
                        q3.setRandInt(randInt);
                    }
                    case 4 -> {
                        q4.setAnswer(answer);
                        q4.setType(type);
                        q4.setRandInt(randInt);
                    }
                    case 5 -> {
                        q5.setAnswer(answer);
                        q5.setType(type);
                        q5.setRandInt(randInt);
                    }
                }
                switch (type) {
                    case 1 -> setMultChoice(question, choices, Integer.toString(id));
                    case 2 -> setTF(question, Integer.toString(id));
                    case 3 -> setFill(question, Integer.toString(id));
                    case 4 -> setDrop(question, choices, Integer.toString(id));
                }
            } catch (SQLException e) {
                System.err.println("ERROR: " + e);
            } finally {
                DataUtil.close(rs);
                DataUtil.close(statement);
                DataUtil.close(connection);
                id++;
            }
        }
    }
    public void setMultChoice(String question, String dirtyChoices, String id) {
        /*
        This function sets the creates and adds multiple choice questions to the quiz.
         */
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
        choice1.setId(id);
        value = random.nextInt(4);
        choice1.setText(listchoices.get(value));
        listchoices.remove(value);
        choice1.getStyleClass().add("toggleButtons");
        hBox1.getChildren().add(l1);
        hBox1.getChildren().add(choice1);
        ToggleButton choice2 = new ToggleButton();
        value = random.nextInt(3);
        choice2.setText(listchoices.get(value));
        choice2.setId(id);
        listchoices.remove(value);
        choice2.getStyleClass().add("toggleButtons");
        hBox2.getChildren().add(l2);
        hBox2.getChildren().add(choice2);
        ToggleButton choice3 = new ToggleButton();
        value = random.nextInt(2);
        choice3.setText(listchoices.get(value));
        choice3.setId(id);
        listchoices.remove(value);
        choice3.getStyleClass().add("toggleButtons");
        hBox3.getChildren().add(l3);
        hBox3.getChildren().add(choice3);
        ToggleButton choice4 = new ToggleButton();
        choice4.setText(listchoices.get(0));
        choice4.setId(id);
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
        stack.getChildren().add(rectangle);
        stack.getChildren().add(innerBox);
        quizBox.getChildren().add(stack);
    }
    public void setTF(String question, String id) {

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
        trueButton.setId(id);
        trueButton.setText("True");
        trueButton.getStyleClass().add("toggleButtons");
        ToggleButton falseButton = new ToggleButton();
        falseButton.setId(id);
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
        stack.getChildren().add(rectangle);
        stack.getChildren().add(innerBox);
        quizBox.getChildren().add(stack);
    }
    /*
        Alignment issues, due to time constraints I will come back to this.

    public void setFill(String question, String answer) {
        FlowPane qPane = new FlowPane();
        StackPane stack = new StackPane();
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(900);
        rectangle.setArcHeight(50);
        rectangle.setArcWidth(50);
        rectangle.setFill(WHITE);
        rectangle.setLayoutX(510);
        qPane.maxWidth(900);
        qPane.maxHeight(300);
        qPane.setPadding(new Insets(50,20,20,40));
        qPane.setHgap(10);
        qPane.setOrientation(Orientation.HORIZONTAL);
        qPane.s
        String[] questionFrag = question.split(",");
        Label qLabel1 = new Label();
        qLabel1.setText(questionFrag[0]);
        qLabel1.getStyleClass().add("fill");
        Label qLabel2 = new Label();
        qLabel2.setText(questionFrag[1]);
        qLabel2.getStyleClass().add("fill");
        qLabel2.setWrapText(true);
        TextField fill = new TextField();
        fill.setPrefHeight(25);
        fill.setPrefWidth(50);
        fill.setMaxHeight(25);
        fill.setMaxWidth(50);
        qPane.getChildren().add(qLabel1);
        qPane.getChildren().add(fill);
        qPane.getChildren().add(qLabel2);
        rectangle.setHeight(300);
        stack.getChildren().add(rectangle);
        stack.getChildren().add(qPane);
        quizBox.getChildren().add(stack);
    }

     */
    public void setFill(String question, String id) {
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
        fill.setId(id);
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
        stack.getChildren().add(rectangle);
        stack.getChildren().add(innerBox);
        quizBox.getChildren().add(stack);
    }
    public void setDrop(String question, String dirtyChoices, String id) {
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
        drop.setId(id);
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
        stack.getChildren().add(rectangle);
        stack.getChildren().add(innerBox);
        quizBox.getChildren().add(stack);
    }
    public void setSubmit(){
        Button submit = new Button("Submit");
        submit.getStyleClass().add("richBlue");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean control = true;
                try {
                    for (Node stack: quizBox.getChildren()) {
                        if (stack instanceof StackPane) {
                            for(Node vbox: ((StackPane) stack).getChildren()) {
                                if (vbox instanceof VBox) {
                                    for (Node gen3: ((VBox) vbox).getChildren()) {
                                        if (gen3 instanceof VBox){
                                            for (Node gen4: ((VBox) gen3).getChildren()){
                                                if(gen4 instanceof ToggleButton){
                                                    if (((ToggleButton) gen4).getToggleGroup().getSelectedToggle().isSelected()) {
                                                        if (((ToggleButton) gen4).isSelected()){
                                                            String answer = ((ToggleButton) gen4).getText();
                                                            String dirtyID = gen4.getId();
                                                            int id = Integer.parseInt(dirtyID);
                                                            switch (id) {
                                                                case 1 -> {
                                                                    String realAnswer = q1.getAnswer();
                                                                    if (realAnswer.equals("1")) realAnswer = "True";
                                                                    else realAnswer = "False";
                                                                    q1.setCorrect(realAnswer.equals(answer));
                                                                } case 2 -> {
                                                                    String realAnswer = q2.getAnswer();
                                                                    if (realAnswer.equals("1")) realAnswer = "True";
                                                                    else realAnswer = "False";
                                                                    q2.setCorrect(realAnswer.equals(answer));
                                                                } case 3 -> {
                                                                    String realAnswer = q3.getAnswer();
                                                                    if (realAnswer.equals("1")) realAnswer = "True";
                                                                    else realAnswer = "False";
                                                                    q3.setCorrect(realAnswer.equals(answer));
                                                                } case 4 -> {
                                                                    String realAnswer = q4.getAnswer();
                                                                    if (realAnswer.equals("1")) realAnswer = "True";
                                                                    else realAnswer = "False";
                                                                    q4.setCorrect(realAnswer.equals(answer));
                                                                } case 5 -> {
                                                                    String realAnswer = q5.getAnswer();
                                                                    if (realAnswer.equals("1")) realAnswer = "True";
                                                                    else realAnswer = "False";
                                                                    q5.setCorrect(realAnswer.equals(answer));
                                                                }
                                                            }
                                                            break;
                                                        }
                                                    } else {
                                                        StackPane errorStack = new StackPane();
                                                        Rectangle rect = new Rectangle();
                                                        rect.setArcWidth(10);
                                                        rect.setArcHeight(10);
                                                        rect.setHeight(100);
                                                        rect.setWidth(400);
                                                        rect.getStyleClass().add("errorRect");
                                                        Label error = new Label("Please answer every question before submitting.");
                                                        error.getStyleClass().add("blackLabels");
                                                        errorStack.getChildren().addAll(rect, error);
                                                        quizBox.getChildren().add(errorStack);
                                                        control = false;
                                                    }
                                                } else if (gen4 instanceof HBox) {
                                                    for (Node toggle : ((HBox) gen4).getChildren()) {
                                                        if (toggle instanceof ToggleButton) {
                                                            try{
                                                                if (((ToggleButton) toggle).getToggleGroup().getSelectedToggle().isSelected()) {
                                                                    if (((ToggleButton) toggle).isSelected()){
                                                                        String answer = ((ToggleButton) toggle).getText();
                                                                        String dirtyID = toggle.getId();
                                                                        int id = Integer.parseInt(dirtyID);
                                                                        switch (id) {
                                                                            case 1 -> {
                                                                                String realAnswer = q1.getAnswer();
                                                                                q1.setCorrect(realAnswer.equals(answer));
                                                                            } case 2 -> {
                                                                                String realAnswer = q2.getAnswer();
                                                                                q2.setCorrect(realAnswer.equals(answer));
                                                                            } case 3 -> {
                                                                                String realAnswer = q3.getAnswer();
                                                                                q3.setCorrect(realAnswer.equals(answer));
                                                                            } case 4 -> {
                                                                                String realAnswer = q4.getAnswer();
                                                                                q4.setCorrect(realAnswer.equals(answer));
                                                                            } case 5 -> {
                                                                                String realAnswer = q5.getAnswer();
                                                                                q5.setCorrect(realAnswer.equals(answer));
                                                                            }
                                                                        }
                                                                        break;
                                                                    }
                                                                }
                                                            } catch (NullPointerException e) {
                                                                StackPane errorStack = new StackPane();
                                                                Rectangle rect = new Rectangle();
                                                                rect.setArcWidth(10);
                                                                rect.setArcHeight(10);
                                                                rect.setHeight(100);
                                                                rect.setWidth(400);
                                                                rect.getStyleClass().add("errorRect");
                                                                Label error = new Label("Please answer every question before submitting.");
                                                                error.getStyleClass().add("blackLabels");
                                                                errorStack.getChildren().addAll(rect, error);
                                                                quizBox.getChildren().add(errorStack);
                                                                control = false;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (gen3 instanceof HBox) {
                                            for (Node gen4: ((HBox) gen3).getChildren()) {
                                                if (gen4 instanceof TextField) {
                                                    String answer = ((TextField) gen4).getText();
                                                    String dirtyID = gen4.getId();
                                                    int id = Integer.parseInt(dirtyID);
                                                    if (answer.length() == 0) {
                                                        StackPane errorStack = new StackPane();
                                                        Rectangle rect = new Rectangle();
                                                        rect.setArcWidth(10);
                                                        rect.setArcHeight(10);
                                                        rect.setHeight(100);
                                                        rect.setWidth(400);
                                                        rect.getStyleClass().add("errorRect");
                                                        Label error = new Label("Please answer every question before submitting.");
                                                        error.getStyleClass().add("blackLabels");
                                                        errorStack.getChildren().addAll(rect, error);
                                                        quizBox.getChildren().add(errorStack);
                                                        control = false;
                                                    } else {
                                                        switch (id) {
                                                            case 1 -> {
                                                                String realAnswer = q1.getAnswer();
                                                                q1.setCorrect(realAnswer.equals(answer));
                                                            } case 2 -> {
                                                                String realAnswer = q2.getAnswer();
                                                                q2.setCorrect(realAnswer.equals(answer));
                                                            } case 3 -> {
                                                                String realAnswer = q3.getAnswer();
                                                                q3.setCorrect(realAnswer.equals(answer));
                                                            } case 4 -> {
                                                                String realAnswer = q4.getAnswer();
                                                                q4.setCorrect(realAnswer.equals(answer));
                                                            } case 5 -> {
                                                                String realAnswer = q5.getAnswer();
                                                                q5.setCorrect(realAnswer.equals(answer));
                                                            }
                                                        }
                                                    }
                                                } else if (gen4 instanceof ChoiceBox) {
                                                    String answer = ((ChoiceBox<String>) gen4).getValue();
                                                    String dirtyID = gen4.getId();
                                                    int id = Integer.parseInt(dirtyID);
                                                    try {
                                                        if (answer.length() != 0){
                                                            switch (id) {
                                                                case 1 -> {
                                                                    String realAnswer = q1.getAnswer();
                                                                    q1.setCorrect(realAnswer.equals(answer));
                                                                } case 2 -> {
                                                                    String realAnswer = q2.getAnswer();
                                                                    q2.setCorrect(realAnswer.equals(answer));
                                                                } case 3 -> {
                                                                    String realAnswer = q3.getAnswer();
                                                                    q3.setCorrect(realAnswer.equals(answer));
                                                                } case 4 -> {
                                                                    String realAnswer = q4.getAnswer();
                                                                    q4.setCorrect(realAnswer.equals(answer));
                                                                } case 5 -> {
                                                                    String realAnswer = q5.getAnswer();
                                                                    q5.setCorrect(realAnswer.equals(answer));
                                                                }
                                                            }
                                                        }
                                                    } catch (NullPointerException e) {
                                                        StackPane errorStack = new StackPane();
                                                        Rectangle rect = new Rectangle();
                                                        rect.setArcWidth(10);
                                                        rect.setArcHeight(10);
                                                        rect.setHeight(100);
                                                        rect.setWidth(400);
                                                        rect.getStyleClass().add("errorRect");
                                                        Label error = new Label("Please answer every question before submitting.");
                                                        error.getStyleClass().add("blackLabels");
                                                        errorStack.getChildren().addAll(rect, error);
                                                        quizBox.getChildren().add(errorStack);
                                                        control = false;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (control) {
                        try {
                            setViewScore();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (ConcurrentModificationException ignored) {}
            }
        });
        quizBox.getChildren().add(submit);
    }

    /**
     * Method that is called if all questions are answered and the "Submit" button is clicked.
     * Opens ViewScore.fxml.
     *
     * @throws IOException
     */
    public void setViewScore() throws IOException {
        Stage stage = (Stage) quizBox.getScene().getWindow();

        stage.close();
        Stage nextStage = new Stage();
        Pane root = FXMLLoader.load(getClass().getResource("/Quiz/SubWindows/ViewScore/ViewScore.fxml"));
        Scene scene = new Scene(root);
        nextStage.setResizable(false);
        nextStage.setScene(scene);
        nextStage.show();
    }
}

