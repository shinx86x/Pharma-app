package com.example.drugsearch;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText uN;
    private EditText pW;
    private TextView check;

    public void checkUserPass(View v) {
        Button button = (Button) v;
        uN = findViewById(R.id.username);
        pW = findViewById(R.id.password);
        check = findViewById(R.id.checkUp);

        String user1 = "GrandmaJenkins";
        String pass1 = "speedyRecov123";
        String cmp1 = String.valueOf(uN.getText());
        String cmp2 = String.valueOf(pW.getText()); // Will get this working

        if (user1.equals(cmp1)) {
            check.setText("Successful Login!");
            check.setVisibility(View.VISIBLE);

            // Navigate to MenuActivity after successful login
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        } else {
            check.setText("Username/Password is Incorrect");
            check.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
