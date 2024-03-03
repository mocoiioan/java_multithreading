package com.example.assignment2.GUI;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.util.Duration;

public class SimulationFrameView {
    private Label NLabel;
    private Label QLabel;
    private Label timeLimitLabel;
    private Label minArrivalLabel;
    private Label maxArrivalLabel;
    private Label minServiceLabel;
    private Label maxServiceLabel;

    private TextField NTextField;
    private TextField QTextField;
    private TextField timeLimitTextField;
    private TextField minArrivalTextField;
    private TextField maxArrivalTextField;
    private TextField minServiceTextField;
    private TextField maxServiceTextField;
    private Button startButton;
    public TextArea textArea;

    public Timeline timeline;

    public SimulationFrameView(){
        NLabel = new Label("N: ");
        NLabel.setLayoutX(20);
        NLabel.setLayoutY(20);

        QLabel = new Label("Q: ");
        QLabel.setLayoutX(20);
        QLabel.setLayoutY(50);

        timeLimitLabel = new Label("Time Limit: ");
        timeLimitLabel.setLayoutX(20);
        timeLimitLabel.setLayoutY(80);

        minArrivalLabel = new Label("Min Arrival: ");
        minArrivalLabel.setLayoutX(20);
        minArrivalLabel.setLayoutY(110);

        maxArrivalLabel = new Label("Max Arrival: ");
        maxArrivalLabel.setLayoutX(20);
        maxArrivalLabel.setLayoutY(140);

        minServiceLabel = new Label("Min Service: ");
        minServiceLabel.setLayoutX(20);
        minServiceLabel.setLayoutY(170);

        maxServiceLabel = new Label("Max Service: ");
        maxServiceLabel.setLayoutX(20);
        maxServiceLabel.setLayoutY(200);

        NTextField = new TextField("4");
        NTextField.setLayoutX(100);
        NTextField.setLayoutY(20);
        NTextField.setPrefWidth(50);

        QTextField = new TextField("2");
        QTextField.setLayoutX(100);
        QTextField.setLayoutY(50);
        QTextField.setPrefWidth(50);

        timeLimitTextField = new TextField("60");
        timeLimitTextField.setLayoutX(100);
        timeLimitTextField.setLayoutY(80);
        timeLimitTextField.setPrefWidth(50);

        minArrivalTextField = new TextField("2");
        minArrivalTextField.setLayoutX(100);
        minArrivalTextField.setLayoutY(110);
        minArrivalTextField.setPrefWidth(50);

        maxArrivalTextField = new TextField("30");
        maxArrivalTextField.setLayoutX(100);
        maxArrivalTextField.setLayoutY(140);
        maxArrivalTextField.setPrefWidth(50);

        minServiceTextField = new TextField("2");
        minServiceTextField.setLayoutX(100);
        minServiceTextField.setLayoutY(170);
        minServiceTextField.setPrefWidth(50);

        maxServiceTextField = new TextField("4");
        maxServiceTextField.setLayoutX(100);
        maxServiceTextField.setLayoutY(200);
        maxServiceTextField.setPrefWidth(50);

        textArea = new TextArea("Simulation Area");
        textArea.setWrapText(true);
        textArea.setEditable(true);
        textArea.setLayoutX(200);
        textArea.setLayoutY(20);
        textArea.setPrefWidth(250);

        timeline = new Timeline(new KeyFrame(Duration.millis(200), SimulationFrameController.update));

        timeline.setCycleCount(Animation.INDEFINITE);

        startButton = new Button("Start");
        startButton.setLayoutX(20);
        startButton.setLayoutY(230);
        startButton.setPrefWidth(130);
        startButton.setOnAction(SimulationFrameController.start);

    }

    public void setTextArea(String text) {
        textArea.appendText(text);
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public Label getNLabel() {
        return NLabel;
    }

    public Label getQLabel() {
        return QLabel;
    }

    public Label getTimeLimitLabel() {
        return timeLimitLabel;
    }

    public Label getMinArrivalLabel() {
        return minArrivalLabel;
    }

    public Label getMaxArrivalLabel() {
        return maxArrivalLabel;
    }

    public Label getMinServiceLabel() {
        return minServiceLabel;
    }

    public Label getMaxServiceLabel() {
        return maxServiceLabel;
    }

    public TextField getNTextField() {
        return NTextField;
    }

    public TextField getQTextField() {
        return QTextField;
    }

    public TextField getTimeLimitTextField() {
        return timeLimitTextField;
    }

    public TextField getMinArrivalTextField() {
        return minArrivalTextField;
    }

    public TextField getMaxArrivalTextField() {
        return maxArrivalTextField;
    }

    public TextField getMinServiceTextField() {
        return minServiceTextField;
    }

    public TextField getMaxServiceTextField() {
        return maxServiceTextField;
    }

    public Button getStartButton() {
        return startButton;
    }
}
