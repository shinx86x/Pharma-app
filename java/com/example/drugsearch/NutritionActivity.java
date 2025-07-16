package com.example.drugsearch;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;

public class NutritionActivity extends AppCompatActivity {
    private EditText drugNameEditText;
    private Button searchButton;
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        drugNameEditText = findViewById(R.id.nutritionDrugNameEditText);
        searchButton = findViewById(R.id.nutritionSearchButton);
        exitButton = findViewById(R.id.exitButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String drugName = drugNameEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(drugName)) {
                    searchDrug(drugName);
                }
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle navigation back to MainActivity
                Intent intent = new Intent(NutritionActivity.this, InformationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void searchDrug(String drugName) {
        DrugDatabase db = DrugDatabase.getInstance(this);

        DrugDao drugDao = db.drugDao();

        DrugSearchRunnable searchRunnable = new DrugSearchRunnable(drugDao, drugName);
        DrugDatabase.databaseWriteExecutor.execute(searchRunnable);
    }

    private class DrugSearchRunnable implements Runnable {
        private final DrugDao drugDao;
        private final String drugName;

        public DrugSearchRunnable(DrugDao drugDao, String drugName) {
            this.drugDao = drugDao;
            this.drugName = drugName;
        }

        @Override
        public void run() {
            List<Drug> foundDrugs = drugDao.getDrugByName(drugName);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!foundDrugs.isEmpty()) {
                        Drug foundDrug = foundDrugs.get(0); // Just take the first drug if there are multiple
                        Intent intent = new Intent(NutritionActivity.this, NutritionTabActivity.class);
                        intent.putExtra("drugName", foundDrug.getDrugName());
                        intent.putExtra("nutritionLabel", foundDrug.getNutritionLabel());
                        startActivity(intent);
                    } else {
                        Toast.makeText(NutritionActivity.this, "Drug not found in database", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}



