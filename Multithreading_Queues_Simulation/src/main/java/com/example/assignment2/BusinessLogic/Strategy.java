package com.example.assignment2.BusinessLogic;

import com.example.assignment2.Model.Server;
import com.example.assignment2.Model.Task;

import java.util.List;

public interface Strategy {
    public void addTask(List<Server> servers, Task t) throws InterruptedException;
}
