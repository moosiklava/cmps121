package com.example.mdelc.pettracker;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by remea on 6/7/2018.
 */

public class PetGroup implements Serializable {

    public String groupPetName;
    public String groupPetOwner;
    public int petPic;
    public int petOwnerPic;
    public ArrayList<QuickTask> groupQuickTaskList;
    public ArrayList<PetTask> groupPetTaskList;

    PetGroup(String petName, String petOwnerName, int petPicture, int petOwnerPicture){
        groupPetName = petName;
        groupPetOwner = petOwnerName;
        groupQuickTaskList = new ArrayList<>();
        groupPetTaskList = new ArrayList<>();
        petPic = petPicture;
        petOwnerPic = petOwnerPicture;

    }
}
