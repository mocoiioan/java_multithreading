package com.example.assignment2.GUI;

import com.example.assignment2.BusinessLogic.Scheduler;
import com.example.assignment2.SimulationManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class SimulationFrameController {
    private static SimulationFrameView simulationFrameView;

    private static SimulationManager simulationManager;
    public SimulationFrameController(SimulationFrameView SFV) {
        simulationFrameView = SFV;
    }

    public static EventHandler<ActionEvent> start = actionEvent -> {
        simulationManager = new SimulationManager();
        simulationManager.setTimeLimit(Integer.parseInt(simulationFrameView.getTimeLimitTextField().getText()));
        simulationManager.setMaxArrivalTime(Integer.parseInt(simulationFrameView.getMaxArrivalTextField().getText()));
        simulationManager.setMinArrivalTime(Integer.parseInt(simulationFrameView.getMinArrivalTextField().getText()));
        simulationManager.setMaxProcessingTime(Integer.parseInt(simulationFrameView.getMaxServiceTextField().getText()));
        simulationManager.setMinProcessingTime(Integer.parseInt(simulationFrameView.getMinServiceTextField().getText()));
        simulationManager.setNumberOfServers(Integer.parseInt(simulationFrameView.getQTextField().getText()));
        simulationManager.setNumberOfClients(Integer.parseInt(simulationFrameView.getNTextField().getText()));
        simulationManager.generateNRandomTasks(simulationManager.numberOfClients);
        simulationManager.setScheduler(simulationManager.numberOfServers, simulationManager.selectionPolicy);
        Thread t = new Thread(simulationManager);
        t.start();
        simulationFrameView.timeline.play();
    };

    public static EventHandler<ActionEvent> update = actionEvent -> {
        simulationFrameView.getTextArea().setText(simulationManager.getUpdateString());
    };
}