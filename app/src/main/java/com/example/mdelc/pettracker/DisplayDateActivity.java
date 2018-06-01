package com.example.calendarandgoogle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.widget.Button;

public class DisplayDateActivity extends AppCompatActivity {

    private static final String TAG = "DisplayDateActivity";

    private Button btnGoCal;
    private TextView theDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_date);
        btnGoCal = (Button) findViewById(R.id.btnGoCal);
        theDate = (TextView) findViewById(R.id.theDate);

        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("date");
        theDate.setText(date);

        btnGoCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayDateActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
    }
}


