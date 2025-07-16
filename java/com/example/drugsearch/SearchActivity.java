package com.example.drugsearch;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.hardware.camera2.params.MandatoryStreamCombination;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private EditText drugNameEditText;
    private Button searchButton;
    private Button exitButton; // Add exit button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        drugNameEditText = findViewById(R.id.drugNameEditText);
        searchButton = findViewById(R.id.searchButton);
        exitButton = findViewById(R.id.exitButton); // Initialize exit button

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
                Intent intent = new Intent(SearchActivity.this, InformationActivity.class);
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
                        Drug foundDrug = foundDrugs.get(0);
                        Intent intent = new Intent(SearchActivity.this, DrugDetailsActivity.class);
                        intent.putExtra("drugName", foundDrug.getDrugName());
                        intent.putExtra("drugType", foundDrug.getDrugType());
                        intent.putExtra("usedFor", foundDrug.getUsedFor());
                        intent.putExtra("potentialSideEffects", foundDrug.getPotentialSideEffects());
                        intent.putExtra("safeDuringPregnancy", foundDrug.getSafeDuringPregnancy());
                        intent.putExtra("doNotTakeWith", foundDrug.getDoNotTakeWith());
                        startActivity(intent);
                    } else {
                        Toast.makeText(SearchActivity.this, "Drug not found in database", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}


