package com.example.mdelc.pettracker;

/**
 * Created by remea on 5/2/2018.
 */

public class CompletedPetTask {

    public int taskCompleter;
    public String taskDescription;
    public String timeCompleted;

    CompletedPetTask(){}

    CompletedPetTask(String task, int completer, String time){
        taskDescription = task;
        taskCompleter = completer;
        timeCompleted = time;

    }
}
