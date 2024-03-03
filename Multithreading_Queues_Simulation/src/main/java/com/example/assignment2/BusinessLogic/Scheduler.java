package com.example.assignment2.BusinessLogic;

import com.example.assignment2.Model.Server;
import com.example.assignment2.Model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler {
    private List<Server> servers;
    private Integer maxNoServers;
    private Integer maxTasksPerServing;
    private Strategy strategy;
    private List<Thread> threads;

    public Scheduler(Integer maxNoServers, Integer maxTasksPerServing, SelectionPolicy selectionPolicy) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServing = maxTasksPerServing;
        this.servers = new ArrayList<>(maxNoServers);
        this.threads = new ArrayList<>(maxNoServers);
        changeStrategy(selectionPolicy);
    }

    public void startScheduler() {
        for(int i = 0; i< maxNoServers; i++){
            AtomicInteger atomicInteger = new AtomicInteger(0);
            Server server = new Server(i+1, maxTasksPerServing, atomicInteger);
            servers.add(server);
            threads.add(new Thread(server));
            threads.get(i).start();
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE)
            strategy = new ConcreteStrategyQueue();
        if(policy == SelectionPolicy.SHORTEST_TIME)
            strategy = new ConcreteStrategyQueue();
    }

    public synchronized void dispatchTask(Task t) throws InterruptedException {
        strategy.addTask(servers, t);
    }

    public List<Server> getServers() {
        return servers;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Server s : servers){
            stringBuilder.append("Queue " + s.getId() + ": " + s + "\n");
        }
        return stringBuilder.toString();
    }
}
