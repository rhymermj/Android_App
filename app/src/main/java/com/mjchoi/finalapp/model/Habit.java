package com.mjchoi.finalapp.model;

public class Habit {
    private int id;
    private String habitName;
    private int habitFrequency;
    private int habitProgress;
    private String dateHabitAdded;

    public Habit() {
    }

    public Habit(String habitName, int habitFrequency, String dateItemAdded) {
        this.habitName = habitName;
        this.habitFrequency = habitFrequency;
        this.dateHabitAdded = dateItemAdded;
    }

    public Habit(int id, String habitName, int habitFrequency, String dateItemAdded) {
        this.id = id;
        this.habitName = habitName;
        this.habitFrequency = habitFrequency;
        this.dateHabitAdded = dateItemAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public int getHabitFrequency() {
        return habitFrequency;
    }

    public void setHabitFrequency(int habitFrequency) {
        this.habitFrequency = habitFrequency;
    }

    public int getHabitProgress() {
        return habitProgress;
    }

    public void setHabitProgress(int habitProgress) {
        this.habitProgress = habitProgress;
    }

    public String getDateHabitAdded() {
        return dateHabitAdded;
    }

    public void setDateHabitAdded(String dateHabitAdded) {
        this.dateHabitAdded = dateHabitAdded;
    }
}
