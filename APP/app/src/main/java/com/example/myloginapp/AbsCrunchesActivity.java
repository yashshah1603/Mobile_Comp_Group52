package com.example.myloginapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AbsCrunchesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abs_crunches);
        String sidePlankBenefits = getString(R.string.abs_crunches);
        String sidePlankDuration = getString(R.string.abs_crunches_duration);
        String sidePlankCaloriesBurned = getString(R.string.abs_crunches_calories_burned);

        TextView benefitsTextView = findViewById(R.id.absCrunchesBenefitsTextView);
        TextView durationTextView = findViewById(R.id.absCrunchesDurationTextView);
        TextView caloriesTextView = findViewById(R.id.absCrunchesCaloriesTextView);

        benefitsTextView.setText(sidePlankBenefits);
        durationTextView.setText(sidePlankDuration);
        caloriesTextView.setText(sidePlankCaloriesBurned);
        Button okButton = findViewById(R.id.okButton);
        Button markAsDoneButton = findViewById(R.id.markAsDoneButton);
        markAsDoneButton.setOnClickListener(v -> markActivityAsDone());
        okButton.setOnClickListener(view -> {
            // Redirect to ExerciseActivity
            Intent intent = new Intent(AbsCrunchesActivity.this, ExerciseActivity.class);
            startActivity(intent);
        });
    }

    private void markActivityAsDone() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("markAsDone", true);
        resultIntent.putExtra("duration", 20); // Replace with the actual duration
        resultIntent.putExtra("caloriesBurned", 70); // Replace with the actual calories burned
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
