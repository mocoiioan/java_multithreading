package com.example.assignment2.Model;

public class Task implements Comparable<Task>{
    private Integer ID;
    private Integer arrivalTime;
    private Integer serviceTime;

    public Task(Integer ID, Integer arrivalTime, Integer serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Integer serviceTime) {
        this.serviceTime = serviceTime;
    }

    @Override
    public int compareTo(Task t) {
        return (this.arrivalTime < t.arrivalTime ? -1 : 1);
    }

    @Override
    public String toString() {
        return "(" + ID + " " + arrivalTime + " " + serviceTime + "); ";
    }
}
