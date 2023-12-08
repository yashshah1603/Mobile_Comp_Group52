package com.example.myloginapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class EmergencyPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_page);

        // Find the "End Call" button by its ID
        ImageButton btnEndCall = findViewById(R.id.btnEndCall);

        // Set an OnClickListener for the "End Call" button
        btnEndCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event
                goToMeasureHeartAndRespRateActivity();
            }
        });
    }

    // Method to navigate to the MeasureHeartAndRespRateActivity
    private void goToMeasureHeartAndRespRateActivity() {
        Intent intent = new Intent(this, RedirectPage.class);
        startActivity(intent);
        finish(); // Close the EmergencyPageActivity
    }
}
