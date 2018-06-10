package com.example.mdelc.pettracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SitterProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_profile);
    }


    public void onResume(){
        super.onResume();
        Button b = findViewById(R.id.button_edit);
        JSONObject jo = null;
        final Context context = this;

        try{
            File f = new File(getFilesDir(), "file.ser");
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            String j = null;
            try{
                j = (String)ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try{
                jo = new JSONObject(j);
            }catch(JSONException je){
                je.printStackTrace();
            }
            fis.close();
            ois.close();

            try{
                TextView name, email, home, phone, bio;
                name = findViewById(R.id.nameText);
                name.setText(jo.getString("name"));
                email = findViewById(R.id.emailText);
                email.setText(jo.getString("email"));
                home = findViewById(R.id.homeText);
                home.setText(jo.getString("home"));
                phone = findViewById(R.id.phoneText);
                phone.setText(jo.getString("phone"));
                bio = findViewById(R.id.bioText);
                bio.setText(jo.getString("bio"));
            }catch(JSONException je){
                je.printStackTrace();
            }


        }catch(FileNotFoundException nf){
            nf.printStackTrace();
            TextView name, email, home, phone, bio;
            name = findViewById(R.id.nameText);
            name.setText("Name");
            email = findViewById(R.id.emailText);
            email.setText("example@email.com");
            home = findViewById(R.id.homeText);
            home.setText("Santa Cruz, CA");
            phone = findViewById(R.id.phoneText);
            phone.setText("+0-000-0000");
            bio = findViewById(R.id.bioText);
            bio.setText("Short Bio Here");

        } catch (IOException e) {
            e.printStackTrace();
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditProfile.class);
                startActivity(i);
            }
        });


    }


}
