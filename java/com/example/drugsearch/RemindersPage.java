package com.example.drugsearch;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class RemindersPage extends AppCompatActivity {

    private static final String PREFS_NAME = "PrescriptionsPrefs";
    private static final String PRESCRIPTIONS_KEY = "prescriptions";
    private static final String REMINDERS_KEY = "reminders";

    ArrayList<String> prescriptions;
    ArrayList<String> reminders;
    LinearLayout remindersLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders_page);

        // Load the prescriptions and reminders
        prescriptions = new ArrayList<>(loadPrescriptions());
        reminders = new ArrayList<>(loadReminders());

        // Initialize the LinearLayout for reminders
        remindersLayout = findViewById(R.id.remindersLayout);

        // Add existing reminders to the layout
        for (String reminder : reminders) {
            addReminder(reminder);
        }

        // Set onclick listener for Set_Reminder_Button
        Button setReminderButton = findViewById(R.id.Set_Reminder_Button);
        setReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating an alert dialog with prescriptions
                AlertDialog.Builder builder = new AlertDialog.Builder(RemindersPage.this);
                builder.setTitle("Select a prescription to set a reminder");

                // Set up the items - this can be your prescriptions list
                builder.setItems(prescriptions.toArray(new String[0]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // This will be triggered when a prescription is clicked.
                        String selectedPrescription = prescriptions.get(which);
                        // Open another dialog to select the reminder time
                        openReminderTimeDialog(selectedPrescription);
                    }
                });
                builder.show();
            }
        });

        // Find the back button
        ImageButton backButton = findViewById(R.id.back_button1);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This will close current activity and return to the previous activity
                finish();
            }
        });

    }

    // This method opens a dialog to select the time
    private void openReminderTimeDialog(String prescription) {
        // Create an array for the days of the week
        final String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        final boolean[] checkedDays = new boolean[7];

        // Create a new AlertDialog for day selection
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select the days")
                .setMultiChoiceItems(days, checkedDays, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedDays[which] = isChecked; // Update the current checked status
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the checkedDays into a separate list
                        ArrayList<String> selectedDays = new ArrayList<>();
                        for (int i = 0; i < 7; i++) {
                            if (checkedDays[i]) { // Checked
                                selectedDays.add(days[i]);
                            }
                        }

                        // Create a new instance of TimePickerDialog and save the final selected days
                        Calendar calendar = Calendar.getInstance();
                        TimePickerDialog timePickerDialog = new TimePickerDialog(RemindersPage.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        // The user has selected a time, add the reminder to the layout
                                        String reminder = prescription + " at " + hourOfDay + ":" + minute + " on " + selectedDays;
                                        addReminder(reminder);
                                        reminders.add(reminder);
                                        saveReminders(reminders);
                                        // TODO: Set up the actual reminder here
                                    }
                                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                                DateFormat.is24HourFormat(RemindersPage.this));
                        timePickerDialog.show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked the negative button
                    }
                });
        builder.create().show();
    }

    private void addReminder(String reminder) {
        View reminderView = getLayoutInflater().inflate(R.layout.list_item, remindersLayout, false);
        TextView content = reminderView.findViewById(R.id.text_content);
        Button deleteButton = reminderView.findViewById(R.id.button_delete);

        // Set the reminder content
        content.setText(reminder);

        // Set the delete button functionality
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog.Builder instance
                AlertDialog.Builder builder = new AlertDialog.Builder(RemindersPage.this);

                // Set the message and the title of the dialog
                builder.setMessage("Are you sure you want to delete this reminder?")
                        .setTitle("Delete Confirmation");

                // Set the positive button and its click listener
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        reminders.remove(reminder); // Remove the reminder from your list
                        remindersLayout.removeView(reminderView); // Remove the view for this reminder
                        saveReminders(reminders);
                    }
                });

                // Set the negative button and its click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog, so we'll just close it
                    }
                });

                // Create the AlertDialog and show it
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // Add the populated view to the layout
        remindersLayout.addView(reminderView);
    }

    // Load prescriptions from SharedPreferences
    private Set<String> loadPrescriptions() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getStringSet(PRESCRIPTIONS_KEY, new HashSet<String>());
    }

    // Save reminders to SharedPreferences
    public void saveReminders(ArrayList<String> remindersList) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        Set<String> set = new HashSet<>(remindersList);
        editor.putStringSet(REMINDERS_KEY, set);
        editor.apply();
    }

    // Load reminders from SharedPreferences
    private Set<String> loadReminders() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getStringSet(REMINDERS_KEY, new HashSet<String>());
    }
}
