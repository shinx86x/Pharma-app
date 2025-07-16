package com.example.drugsearch;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InformationActivity extends AppCompatActivity {

    private Button searchByNameButton;
    private Button searchNutritionButton;
    private Button exitButton; // Add exit button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        searchByNameButton = findViewById(R.id.searchByNameButton);
        searchNutritionButton = findViewById(R.id.searchNutrition);
        exitButton = findViewById(R.id.exitButton); // Initialize exit button

        searchByNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        searchNutritionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationActivity.this, NutritionActivity.class);
                startActivity(intent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
