package com.example.assignment2.BusinessLogic;

import com.example.assignment2.Model.Server;
import com.example.assignment2.Model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) throws InterruptedException {
        int minSize = Integer.MAX_VALUE;
        int id = 0;
        for(Server s : servers)
            if(s.getTasks().size() < minSize) {
                minSize = s.getWaitingPeriod().get();
                id = s.getId();
            }
        for(Server s : servers) {
            if(s.getId() == id) {
                s.addTask(t);
            }
        }
    }
}
