package com.example.myloginapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView headingTextView;
    private Button finishWalkButton;
    private TextView stepCountTextView;
    private Button playPauseButton;
    private int stepCount = 0;
    private boolean isTracking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        headingTextView = findViewById(R.id.headingTextView);
        stepCountTextView = findViewById(R.id.stepCountTextView);
        playPauseButton = findViewById(R.id.playPauseButton);

        // Set initial texts and button state
        headingTextView.setText("Step Counter");
        updateStepCount();
        updatePlayPauseButton();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            Sensor stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }


        playPauseButton.setOnClickListener(v -> toggleTracking());
        finishWalkButton = findViewById(R.id.finishWalkButton);
        finishWalkButton.setOnClickListener(v -> finishWalk());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation4);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_sos) {
                openEmergencyPage();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER && isTracking) {
            stepCount = (int) event.values[0];
            updateStepCount();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void updateStepCount() {
        stepCountTextView.setText("Steps: " + stepCount);
    }

    private void updatePlayPauseButton() {
        playPauseButton.setText(isTracking ? "Pause" : "Play");
    }

    private void toggleTracking() {
        isTracking = !isTracking;
        updatePlayPauseButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null) {
            Sensor stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    private void finishWalk() {
        StepCounterDatabaseHelper dbHelper3 = new StepCounterDatabaseHelper(this);
        uploadStepCount(dbHelper3, stepCount);
        stepCount = 0;
        updateStepCount();
        Toast.makeText(this, "Step count uploaded successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void uploadStepCount(StepCounterDatabaseHelper dbHelper3, int stepCount) {

        ContentValues values = new ContentValues();
        values.put("step_count", stepCount);
        dbHelper3.getWritableDatabase().insert("steps", null, values);
    }
    private void openEmergencyPage() {
        Intent intent = new Intent(this, EmergencyPageActivity.class);
        startActivity(intent);
    }
}
