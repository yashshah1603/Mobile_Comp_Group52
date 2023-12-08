package com.example.myloginapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SidePlankActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_plank);
        String sidePlankBenefits = getString(R.string.side_plank);
        String sidePlankDuration = getString(R.string.side_plank_duration);
        String sidePlankCaloriesBurned = getString(R.string.side_plank_calories_burned);
        Button markAsDoneButton = findViewById(R.id.markAsDoneButton);
        markAsDoneButton.setOnClickListener(v -> markActivityAsDone());
        TextView benefitsTextView = findViewById(R.id.sidePlankBenefitsTextView);
        TextView durationTextView = findViewById(R.id.sidePlankDurationTextView);
        TextView caloriesTextView = findViewById(R.id.sidePlankCaloriesTextView);

        benefitsTextView.setText(sidePlankBenefits);
        durationTextView.setText(sidePlankDuration);
        caloriesTextView.setText(sidePlankCaloriesBurned);

        Button okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(view -> {
            // Redirect to ExerciseActivity
            Intent intent = new Intent(SidePlankActivity.this, ExerciseActivity.class);
            startActivity(intent);
        });
    }
    private void markActivityAsDone() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("markAsDone", true);
        resultIntent.putExtra("duration", 10); // Replace with the actual duration
        resultIntent.putExtra("caloriesBurned", 20); // Replace with the actual calories burned
        setResult(RESULT_OK, resultIntent);
        finish();
    }

}
