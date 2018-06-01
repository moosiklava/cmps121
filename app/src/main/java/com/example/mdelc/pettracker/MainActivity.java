package com.example.mdelc.pettracker;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainPettracker";
    //defining view objects
        private EditText editTextEmail;
        private EditText editTextPassword;
        private Button buttonSignup;
        private TextView textViewSignin;
        private ProgressDialog progressDialog;
        private EditText editTextName;
        FirebaseDatabase database = FirebaseDatabase.getInstance();



    //defining firebaseauth object
        private FirebaseAuth firebaseAuth;
        private FirebaseDatabase firebaseDatabase;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //initializing firebase auth object
            firebaseAuth = FirebaseAuth.getInstance();
            if(firebaseAuth.getCurrentUser() != null)
            {
                //profile activity here
                startActivity(new Intent(getApplicationContext(), MainMenuFinal.class));
            }


            //initializing views
            editTextEmail = (EditText) findViewById(R.id.editTextEmail);
            editTextPassword = (EditText) findViewById(R.id.editTextPassword);
            editTextName = (EditText) findViewById(R.id.editTextName);

            buttonSignup = (Button) findViewById(R.id.buttonSignup);

            progressDialog = new ProgressDialog(this);

            textViewSignin = (TextView) findViewById(R.id.textViewSignin);

            //attaching listener to button
            buttonSignup.setOnClickListener(this);
            textViewSignin.setOnClickListener(this);
        }

        private void registerUser(){

            //getting email and password from edit texts
            final String email = editTextEmail.getText().toString().trim();
            String password  = editTextPassword.getText().toString().trim();
            final String name = editTextName.getText().toString().trim();

            //checking if email and passwords are empty
            if(TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(name)){
                Toast.makeText(this,"Please enter your name",Toast.LENGTH_LONG).show();
                return;
            }

            //if the email and password are not empty
            //displaying a progress dialog

            progressDialog.setMessage("Registering Please Wait...");
            progressDialog.show();

            //creating a new user
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if(task.isSuccessful()){
                                //display some message here
                                progressDialog.hide();
                                //finish();
                                final DatabaseReference databaseReference = database.getReference();
                                DatabaseReference refEmail = databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("email").push();
                                DatabaseReference refName = databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("email").child("name").push();
                                refEmail.setValue(email);
                                refName.setValue(name);
                                DatabaseReference refId = databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("email").child("uid").push();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                refId.setValue(user.getUid());
                                Toast.makeText(MainActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),MainMenuFinal.class));
                            }else{
                                //display some message here
                                progressDialog.hide();
                                Toast.makeText(MainActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });

        }

        @Override
        public void onClick(View view) {
            //calling register method on click
            if(view == buttonSignup)
            {
                registerUser();
            }
            if(view == textViewSignin)
            {
                startActivity(new Intent(this, LoginActivity.class));
            }
        }
    }
