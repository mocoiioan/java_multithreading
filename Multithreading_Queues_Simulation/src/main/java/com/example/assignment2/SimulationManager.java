package com.example.assignment2;

import com.example.assignment2.BusinessLogic.Scheduler;
import com.example.assignment2.BusinessLogic.SelectionPolicy;
import com.example.assignment2.GUI.SimulationFrameController;
import com.example.assignment2.GUI.SimulationFrameView;
import com.example.assignment2.Model.Server;
import com.example.assignment2.Model.Task;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class SimulationManager extends Application implements Runnable {
    public Integer timeLimit;
    public Integer maxProcessingTime;
    public Integer minProcessingTime;
    public Integer maxArrivalTime;
    public Integer minArrivalTime;
    public Integer numberOfServers;
    public Integer numberOfClients;
    public Double avgWaitingTime = 0.0;
    public Double avgServiceTime = 0.0;
    public Integer peakHour = 0;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler;
    private List<Task> generatedTasks;
    public SimulationFrameView SFV;
    File file;
    public FileWriter fileWriter;

    public String updateString = "";

    public SimulationManager() {
        SFV = new SimulationFrameView();
        generatedTasks = new ArrayList<>();
        file = new File("Log.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUpdateString() {
        return updateString;
    }

    public void setScheduler(Integer noOfServers, SelectionPolicy selectionPolicy) {
        this.scheduler = new Scheduler(noOfServers, 5, selectionPolicy);
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void setMaxProcessingTime(Integer maxProcessingTime) {
        this.maxProcessingTime = maxProcessingTime;
    }

    public void setMinProcessingTime(Integer minProcessingTime) {
        this.minProcessingTime = minProcessingTime;
    }

    public void setMaxArrivalTime(Integer maxArrivalTime) {
        this.maxArrivalTime = maxArrivalTime;
    }

    public void setMinArrivalTime(Integer minArrivalTime) {
        this.minArrivalTime = minArrivalTime;
    }

    public void setNumberOfServers(Integer numberOfServers) {
        this.numberOfServers = numberOfServers;
    }

    public void setNumberOfClients(Integer numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public void generateNRandomTasks(Integer n) {
        for (int i = 0; i < n; i++) {
            Random rand = new Random();
            Task task = new Task(i, rand.nextInt(minArrivalTime, maxArrivalTime + 1), rand.nextInt(minProcessingTime, maxProcessingTime + 1));
            generatedTasks.add(task);
        }
        generatedTasks.sort(Task::compareTo);
    }

    public Boolean queuesEmpty() {
        List<Server> serverList;
        serverList = scheduler.getServers();
        for (Server s : serverList) {
            if (s.getTasks().size() != 0) {
                return false;
            }
        }
        return true;
    }

    public Boolean isFirstInQ(Task task){
        List<Server> serverList;
        serverList = scheduler.getServers();
        for(Server s : serverList){
            BlockingQueue<Task> taskBlockingQueue = s.getTasks();
            if(taskBlockingQueue.element() == task)
                return true;
        }
        return false;
    }

    public Integer getWaitingClients() {
        Integer totalWaitingClients = 0;
        List<Server> serverList;
        serverList = scheduler.getServers();
        for (Server s : serverList) {
            totalWaitingClients += s.getTasks().size();
        }
        return totalWaitingClients;
    }

    @Override
    public void run() {
        scheduler.startScheduler();
        Integer currentTime = 0;
        Integer maxWaitingClients = 0;
        Integer currentArrivalTime = 0;

        try {
            fileWriter = new FileWriter("Log.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (currentTime < timeLimit || !generatedTasks.isEmpty()) {
            if (queuesEmpty() && generatedTasks.isEmpty()) {
                break;
            }

            while (!generatedTasks.isEmpty() && generatedTasks.get(0).getArrivalTime() <= currentTime) {
                try {
                    scheduler.dispatchTask(generatedTasks.get(0));
                    if (isFirstInQ(generatedTasks.get(0))) {
                        currentArrivalTime = generatedTasks.get(0).getArrivalTime();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                avgServiceTime += generatedTasks.get(0).getServiceTime();
                generatedTasks.remove(0);
            }
            avgWaitingTime+=currentTime-currentArrivalTime;

            if (getWaitingClients() > maxWaitingClients) {
                maxWaitingClients = getWaitingClients();
                peakHour = currentTime;
            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Time: " + currentTime + "\n");
            for (Task t : generatedTasks) {
                stringBuilder.append(t);
            }
            stringBuilder.append("\n" + scheduler + "\n");

            updateString = stringBuilder.toString();

            try {
                fileWriter.write(stringBuilder.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Time: " + currentTime);
            for (Task t : generatedTasks) {
                System.out.print(t);
            }
            System.out.println("\n" + scheduler);

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            currentTime++;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Average waiting time: " + avgWaitingTime / numberOfClients + "\n");
        stringBuilder.append("Average service time: " + avgServiceTime / numberOfClients + "\n");
        stringBuilder.append("Peak Hour: " + peakHour + "\n");

        updateString = stringBuilder.toString();

        try {
            fileWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Average waiting time: " + avgWaitingTime / numberOfClients);
        System.out.println("Average service time: " + avgServiceTime / numberOfClients);
        System.out.println("Peak Hour: " + peakHour);

    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 480, 360);
        stage.setTitle("QSim");
        stage.setScene(scene);
        stage.setResizable(false);

        SimulationFrameController SFC = new SimulationFrameController(SFV);

        root.getChildren().addAll(SFV.getNLabel(), SFV.getQLabel(), SFV.getTimeLimitLabel(), SFV.getMinArrivalLabel(),
                SFV.getMaxArrivalLabel(), SFV.getMinServiceLabel(), SFV.getMaxServiceLabel(),
                SFV.getNTextField(), SFV.getQTextField(), SFV.getTimeLimitTextField(), SFV.getMinArrivalTextField(),
                SFV.getMaxArrivalTextField(), SFV.getMinServiceTextField(), SFV.getMaxServiceTextField(),
                SFV.getStartButton(), SFV.getTextArea());

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}