package com.example.myloginapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class EmergencyPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_page);
        // Add your emergency page content and functionality here
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish(); // Close the EmergencyPageActivity and go back to ExerciseActivity
    }
}
