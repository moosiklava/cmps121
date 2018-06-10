package com.example.mdelc.pettracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    public void onResume(){
        super.onResume();
        Button enter = findViewById(R.id.button_enter);
        final Context context = this;

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jo = null;
                EditText t1, t2, t3, t4, t5;
                String name, bio, email, phone, home;
                t1 = findViewById(R.id.nameEnter);
                t2 = findViewById(R.id.bioEnter);
                t3 = findViewById(R.id.emailEnter);
                t4 = findViewById(R.id.phoneEnter);
                t5 = findViewById(R.id.homeEnter);
                name = t1.getText().toString();
                bio = t2.getText().toString();
                email = t3.getText().toString();
                phone = t4.getText().toString();
                home = t5.getText().toString();

                try{
                    File f = new File(getFilesDir(), "file.ser");
                    FileInputStream fis = new FileInputStream(f);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    String j = null;
                    try {
                        j = (String)ois.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        jo = new JSONObject(j);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    fis.close();
                    ois.close();
                }catch(IOException e){
                    jo = new JSONObject();
                    try {
                        jo.put("name", "");
                        jo.put("bio", "");
                        jo.put("email", "");
                        jo.put("phone", "");
                        jo.put("home", "");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

                    e.printStackTrace();
                }

                if(!name.equals("")){
                    try {
                        jo.put("name", name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(!bio.equals("")){
                    try {
                        jo.put("bio", bio);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(!email.equals("")){
                    try {
                        jo.put("email", email);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(!phone.equals("")){
                    try {
                        jo.put("phone", phone);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(!home.equals("")){
                    try {
                        jo.put("home", home);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                try {

                    FileOutputStream fos = openFileOutput("file.ser", MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(jo.toString());
                    oos.close();
                    fos.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(context, "Saving...", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, MainActivity.class);
                i.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });
    }
}
