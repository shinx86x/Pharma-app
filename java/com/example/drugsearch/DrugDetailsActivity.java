package com.example.drugsearch;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DrugDetailsActivity extends AppCompatActivity {
    private TextView drugNameTextView;
    private TextView drugTypeTextView;
    private TextView usedForTextView;
    private TextView potentialSideEffectsTextView;
    private TextView safeDuringPregnancyTextView;
    private TextView doNotTakeWithTextView;
    private Button backToMainButton;
    private Button backToSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_details);

        drugNameTextView = findViewById(R.id.text_view_drug_name);
        drugTypeTextView = findViewById(R.id.text_view_drug_type);
        usedForTextView = findViewById(R.id.text_view_used_for);
        potentialSideEffectsTextView = findViewById(R.id.text_view_potential_side_effects);
        safeDuringPregnancyTextView = findViewById(R.id.text_view_safe_during_pregnancy);
        doNotTakeWithTextView = findViewById(R.id.text_view_do_not_take_with);
        backToMainButton = findViewById(R.id.button_back_to_main);
        backToSearchButton = findViewById(R.id.button_back_to_search);


        String drugName = getIntent().getStringExtra("drugName");
        String drugType = getIntent().getStringExtra("drugType");
        String usedFor = getIntent().getStringExtra("usedFor");
        String potentialSideEffects = getIntent().getStringExtra("potentialSideEffects");
        String safeDuringPregnancy = getIntent().getStringExtra("safeDuringPregnancy");
        String doNotTakeWith = getIntent().getStringExtra("doNotTakeWith");

        drugNameTextView.setText("Drug Name: " + drugName);
        drugTypeTextView.setText("Drug Type: " + drugType);
        usedForTextView.setText("Used For: " + usedFor);
        potentialSideEffectsTextView.setText("Potential Side Effects: " + potentialSideEffects);
        safeDuringPregnancyTextView.setText("Safe to Take During Pregnancy: " + safeDuringPregnancy);
        doNotTakeWithTextView.setText("Do Not Take With: " + doNotTakeWith);

        ImageView imageViewDrug = findViewById(R.id.image_view_drug);
        String drugImageResourceName = drugName.toLowerCase().replace(" ", "_");
        int imageResourceId = getResources().getIdentifier(
                drugImageResourceName,
                "drawable",
                getPackageName()
        );
        imageViewDrug.setImageResource(imageResourceId);

        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrugDetailsActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        backToSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrugDetailsActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
