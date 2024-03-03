package com.example.assignment2.Model;

import com.example.assignment2.Model.Task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.exit;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private Integer serverId;
    private Double totalWaitingTime;

    public Server(Integer serverId, Integer maxSize, AtomicInteger waitingPeriod) {
        tasks = new ArrayBlockingQueue<>(maxSize);
        this.waitingPeriod = new AtomicInteger(0);
        this.serverId = serverId;
        this.totalWaitingTime = 0.0;
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                exit(-1);
            }
            if (tasks.size() > 0) {
                tasks.element().setServiceTime(tasks.element().getServiceTime() - 1);
                waitingPeriod.decrementAndGet();
                if (tasks.element().getServiceTime() == 0) {
                    try {
                        tasks.take();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }
    }

    public synchronized void addTask(Task newTask) throws InterruptedException {
        tasks.put(newTask);
        totalWaitingTime += newTask.getServiceTime();
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public Double getTotalWaitingTime() {
        return totalWaitingTime;
    }

    @Override
    public String toString() {
        if (getWaitingPeriod().get() == 0)
            return "closed Q";
        else
            return tasks.toString();
    }

    public Integer getId() {
        return serverId;
    }
}
