package com.example.assignment2.BusinessLogic;

import com.example.assignment2.Model.Server;
import com.example.assignment2.Model.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) throws InterruptedException {
        int minTime = Integer.MAX_VALUE;
        int id = 0;
        for(Server s : servers)
            if(s.getWaitingPeriod().get() < minTime) {
                minTime = s.getWaitingPeriod().get();
                id = s.getId();
            }
        for(Server s : servers) {
            if(s.getId() == id) {
                s.addTask(t);
            }
        }
    }
}
