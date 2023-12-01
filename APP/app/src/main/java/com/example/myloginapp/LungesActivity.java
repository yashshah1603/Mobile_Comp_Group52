package com.example.myloginapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LungesActivity extends AppCompatActivity {
    private boolean isMarkedAsDone = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunges);
        String sidePlankBenefits = getString(R.string.lunges);
        String sidePlankDuration = getString(R.string.lunges_duration);
        String sidePlankCaloriesBurned = getString(R.string.lunges_calories_burned);

        TextView benefitsTextView = findViewById(R.id.lungesBenefitsTextView);
        TextView durationTextView = findViewById(R.id.lungesDurationTextView);
        TextView caloriesTextView = findViewById(R.id.lungesCaloriesTextView);

        benefitsTextView.setText(sidePlankBenefits);
        durationTextView.setText(sidePlankDuration);
        caloriesTextView.setText(sidePlankCaloriesBurned);
        Button okButton = findViewById(R.id.okButton);
        Button markAsDoneButton = findViewById(R.id.markAsDoneButton);
        markAsDoneButton.setOnClickListener(v -> markActivityAsDone());
        okButton.setOnClickListener(view -> {
            // Redirect to ExerciseActivity
            Intent intent = new Intent(LungesActivity.this, ExerciseActivity.class);
            startActivity(intent);
        });
    }
    private void markActivityAsDone() {
        isMarkedAsDone = true;
    }

    public boolean isMarkedAsDone() {
        return isMarkedAsDone;
    }
}
