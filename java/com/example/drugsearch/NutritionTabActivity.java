package com.example.drugsearch;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NutritionTabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_tab);

        String nutritionLabel = getIntent().getStringExtra("nutritionLabel");

        int imageResourceId = getResources().getIdentifier(
                nutritionLabel,
                "drawable",
                getPackageName()
        );

        ImageView imageViewNutritionTab = findViewById(R.id.imageViewNutritionTab);
        imageViewNutritionTab.setImageResource(imageResourceId);

        Button backButtonToSearch = findViewById(R.id.buttonBackToSearch);
        backButtonToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutritionTabActivity.this, NutritionActivity.class);
                startActivity(intent);
            }
        });

        Button backButtonToMenu = findViewById(R.id.buttonBackToMenu);
        backButtonToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutritionTabActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}

