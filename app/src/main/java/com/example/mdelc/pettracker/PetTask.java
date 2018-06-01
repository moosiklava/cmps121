package com.example.mdelc.pettracker;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by remea on 4/27/2018.
 */

public class PetTask {

    public int taskCreator;
    public int taskDesignee;
    public String taskDescription;
    public Boolean isCompleted = false;

    PetTask(){}

    PetTask(String task, int creator, int designee){
        taskDescription = task;
        taskCreator = creator;
        taskDesignee = designee;
    }

    public String getTaskDescription(){
        return taskDescription;
    }


}
