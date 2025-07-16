package com.example.drugsearch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PrescriptionsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "PrescriptionsPrefs";
    private static final String PRESCRIPTIONS_KEY = "prescriptions";

    ArrayList<String> prescriptions;
    NumberedArrayAdapter itemsAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescriptions);

        // an array of prescriptions
        prescriptions = new ArrayList<>(loadPrescriptions());

        // create an ArrayAdapter from the prescriptions array
        itemsAdapter = new NumberedArrayAdapter(this, R.layout.list_item, prescriptions, true);

        // get the ListView and set the ArrayAdapter as its adapter
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(itemsAdapter);

        Button addButton = (Button) findViewById(R.id.button_add_prescription);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating an alert dialog for user input
                AlertDialog.Builder builder = new AlertDialog.Builder(PrescriptionsActivity.this);
                builder.setTitle("Please type in your prescription");

                // Set up the input
                final EditText input = new EditText(PrescriptionsActivity.this);
                builder.setView(input);

                // Setting up the buttons
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Add the user input to the list and update the listView
                        prescriptions.add(input.getText().toString());
                        itemsAdapter.notifyDataSetChanged();
                        // Save the updated prescriptions list
                        savePrescriptions(prescriptions);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        ImageButton backButton = (ImageButton) findViewById(R.id.back_button2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // go back to the previous activity
            }
        });
    }

    // Method to save the prescriptions in SharedPreferences
    public void savePrescriptions(ArrayList<String> prescriptionsList) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        Set<String> set = new HashSet<>(prescriptionsList);
        editor.putStringSet(PRESCRIPTIONS_KEY, set);
        editor.apply();
    }

    // Method to load the prescriptions from SharedPreferences
    private Set<String> loadPrescriptions() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getStringSet(PRESCRIPTIONS_KEY, new HashSet<String>());
    }
}
