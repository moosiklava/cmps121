package com.example.mdelc.pettracker;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by remea on 6/7/2018.
 */

class PetGroup implements Serializable {

    String groupPetName;
    String groupPetOwner;
    int petPic;
    int petOwnerPic;
    ArrayList<QuickTask> groupQuickTaskList;
    ArrayList<PetTask> groupPetTaskList;
    ArrayList<CompletedPetTask> groupCompletedTasks;

    PetGroup(String petName, String petOwnerName, int petPicture, int petOwnerPicture){
        groupPetName = petName;
        groupPetOwner = petOwnerName;
        petPic = petPicture;
        petOwnerPic = petOwnerPicture;
        groupQuickTaskList = new ArrayList<>();
        groupPetTaskList = new ArrayList<>();
        groupCompletedTasks = new ArrayList<>();


    }
}
