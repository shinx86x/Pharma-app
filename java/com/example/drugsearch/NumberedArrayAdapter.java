package com.example.drugsearch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class NumberedArrayAdapter extends ArrayAdapter<String> {

    private boolean isPrescriptionActivity;

    public NumberedArrayAdapter(@NonNull Context context, int resource, @NonNull List<String> objects, boolean isPrescriptionActivity) {
        super(context, resource, objects);
        this.isPrescriptionActivity = isPrescriptionActivity;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView contentTextView = convertView.findViewById(R.id.text_content);
        Button deleteButton = convertView.findViewById(R.id.button_delete);

        // Changed the text format to 'number. prescription'
        contentTextView.setText(String.format("%d. %s", position + 1, getItem(position)));

        // Set up delete button click event
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating an alert dialog for user confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirm Deletion");
                builder.setMessage("Are you sure you want to delete this item?");

                // Setting up the buttons
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Remove the item from the list and update the listView
                        String itemToRemove = getItem(position);
                        remove(itemToRemove);
                        notifyDataSetChanged();

                        if(isPrescriptionActivity) {
                            // Save the updated prescriptions list
                            ((PrescriptionsActivity) getContext()).savePrescriptions(getUpdatedList());
                        } else {
                            // Save the updated reminders list
                            ((RemindersPage) getContext()).saveReminders(getUpdatedList());
                        }
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

        return convertView;
    }

    private ArrayList<String> getUpdatedList() {
        ArrayList<String> updatedList = new ArrayList<>();
        for(int i=0; i<getCount(); i++) {
            updatedList.add(getItem(i));
        }
        return updatedList;
    }
}
