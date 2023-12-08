package com.example.myloginapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class SymptomDetailsActivity extends AppCompatActivity {
    private SymptomDetailsDatabaseHelper dbHelper;
    private Spinner spinner;
    private RatingBar ratingBar;
    private Map<String, Float> ratingsMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_details);

        spinner = findViewById(R.id.spinner);
        ratingBar = findViewById(R.id.ratingBar);
        dbHelper = new SymptomDetailsDatabaseHelper(this);
        ratingsMap = new HashMap<>();

        String[] values = {"Please select a symptom", "Nausea", "Headache", "Diarrhea", "Soar Throat", "Fever", "Muscle Ache", "Loss of Smell or Taste", "Cough",
                "Shortness of Breath", "Feeling Tired"};
        ratingsMap = new HashMap<>();
        for (String value : values) {
            if (!"Please select a symptom".equals(value)) {
                ratingsMap.put(value, 0.0f);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ratingBar.setRating(0.0f);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            String selectedName = spinner.getSelectedItem().toString();
            if (!selectedName.isEmpty() && !"Please select a symptom".equals(selectedName)) {
                ratingsMap.put(selectedName, rating);
            }
        });

        Button uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(v -> {
            dbHelper.insertData(ratingsMap);
            ratingBar.setRating(0.0f);
            spinner.setSelection(0);

        });
        findViewById(R.id.previousPage).setOnClickListener(v -> {
            Intent intent = new Intent( SymptomDetailsActivity.this,UserDetailsActivity.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
