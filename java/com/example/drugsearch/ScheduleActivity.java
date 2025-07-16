package com.example.drugsearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Button prescriptionsButton = (Button) findViewById(R.id.button_prescriptions);
        prescriptionsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activity2Intent = new Intent(getApplicationContext(), PrescriptionsActivity.class);
                startActivity(activity2Intent);
            }
        });

        Button remindersButton = (Button) findViewById(R.id.button_reminders);
        remindersButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                // For now, this button does nothing.
            }
        });

        Button button_prescriptions = findViewById(R.id.button_reminders);
        button_prescriptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to be executed when button2 is clicked
                Intent intent = new Intent(ScheduleActivity.this, RemindersPage.class);
                startActivity(intent);
            }
        });
        Button exitButton = findViewById(R.id.button_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(ScheduleActivity.this, MenuActivity.class);
                startActivity(menuIntent);
                // Finish the ScheduleActivity to prevent coming back here with the back button
                finish();
            }
        });



    }

}
