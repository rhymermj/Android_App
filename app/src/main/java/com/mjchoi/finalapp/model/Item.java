package com.mjchoi.finalapp.model;

public class Item {
    private int id; // Allows to track down each task
    private String taskName;
    private String taskDescription;
    private int taskPriority;
    private String dateItemAdded;

    // Empty constructor
    public Item() {
    }

    public Item(String taskName, String taskDescription, int taskPriority, String dateItemAdded) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskPriority = taskPriority;
        this.dateItemAdded = dateItemAdded;
    }

    public Item(int id, String taskName, String taskDescription, int taskPriority, String dateItemAdded) {
        this.id = id;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskPriority = taskPriority;
        this.dateItemAdded = dateItemAdded;
    }

    // Setters and getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }
}


