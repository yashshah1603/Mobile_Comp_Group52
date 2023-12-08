package com.example.myloginapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LungesActivity extends AppCompatActivity {

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
        Intent resultIntent = new Intent();
        resultIntent.putExtra("markAsDone", true);
        resultIntent.putExtra("duration", 10); // Replace with the actual duration
        resultIntent.putExtra("caloriesBurned", 80); // Replace with the actual calories burned
        setResult(RESULT_OK, resultIntent);
        finish();
    }

}
