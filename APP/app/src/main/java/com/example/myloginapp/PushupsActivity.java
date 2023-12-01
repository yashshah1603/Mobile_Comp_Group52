package com.example.myloginapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PushupsActivity extends AppCompatActivity {
    private boolean isMarkedAsDone = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushups);
        String sidePlankBenefits = getString(R.string.pushups);
        String sidePlankDuration = getString(R.string.pushups_duration);
        String sidePlankCaloriesBurned = getString(R.string.pushups_calories_burned);

        TextView benefitsTextView = findViewById(R.id.pushupsBenefitsTextView);
        TextView durationTextView = findViewById(R.id.pushupsDurationTextView);
        TextView caloriesTextView = findViewById(R.id.pushupsCaloriesTextView);

        benefitsTextView.setText(sidePlankBenefits);
        durationTextView.setText(sidePlankDuration);
        caloriesTextView.setText(sidePlankCaloriesBurned);
        Button okButton = findViewById(R.id.okButton);
        Button markAsDoneButton = findViewById(R.id.markAsDoneButton);
        markAsDoneButton.setOnClickListener(v -> markActivityAsDone());
        okButton.setOnClickListener(view -> {
            // Redirect to ExerciseActivity
            Intent intent = new Intent(PushupsActivity.this, ExerciseActivity.class);
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
