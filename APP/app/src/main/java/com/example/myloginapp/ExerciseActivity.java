package com.example.myloginapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExerciseActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView progressTextView;
    private ExerciseDatabaseHelper dbHelper;
    private Button buttonDone;
    private TextView totalWaterConsumedTodayTextView;
    private int totalWaterConsumedToday = 0;
    private static final String PREF_NAME = "WaterConsumptionPrefs";
    private static final String KEY_CONSUMED_AMOUNT_TODAY = "consumedAmountToday";

    private int totalMinutes = 0;

    private int totalCalories = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        totalWaterConsumedTodayTextView = findViewById(R.id.totalWaterConsumedTextView);
        loadTotalWaterConsumedToday();
        dbHelper = new ExerciseDatabaseHelper(this);
        seekBar = findViewById(R.id.waterSeekBar);
        progressTextView = findViewById(R.id.progressTextView);
        buttonDone = findViewById(R.id.buttonDone);
        Button sidePlankButton = findViewById(R.id.sidePlankButton);
        Button lungesButton = findViewById(R.id.lungesButton);
        Button highSteppingButton = findViewById(R.id.highSteppingButton);
        Button absCrunchesButton = findViewById(R.id.absCrunchesButton);
        Button pushupsButton = findViewById(R.id.pushupsButton);
        sidePlankButton.setOnClickListener(v -> startActivityForResult(new Intent(ExerciseActivity.this, SidePlankActivity.class), 1));
        lungesButton.setOnClickListener(v -> startActivityForResult(new Intent(ExerciseActivity.this, LungesActivity.class), 2));
        highSteppingButton.setOnClickListener(v -> startActivityForResult(new Intent(ExerciseActivity.this, HighSteppingActivity.class), 3));
        absCrunchesButton.setOnClickListener(v -> startActivityForResult(new Intent(ExerciseActivity.this, AbsCrunchesActivity.class), 4));
        pushupsButton.setOnClickListener(v -> startActivityForResult(new Intent(ExerciseActivity.this, PushupsActivity.class), 5));
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation5);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_sos) {
                openEmergencyPage();
                return true;
            }
            return false;
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                saveWaterConsumption(seekBar.getProgress());
            }
        });
        buttonDone.setOnClickListener(v -> redirectToRedirectPage());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            boolean markAsDone = data.getBooleanExtra("markAsDone", false);
            totalMinutes += data.getIntExtra("duration", 0);
            totalCalories += data.getIntExtra("caloriesBurned", 0);

            switch (requestCode) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    if (markAsDone) {
                        displayTotalExerciseDetails(totalMinutes,totalCalories);
                    }
                    break;
            }
        }
    }


    private void updateProgress(int progress) {
        progressTextView.setText("Water Consumption: " + progress + " mL");
    }

    private void saveWaterConsumption(int consumption) {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (!isDateRecorded(currentDate)) {
            insertWaterRecord(currentDate, consumption);
        } else {
            updateWaterRecord(currentDate, consumption);
        }
        totalWaterConsumedToday += consumption;
        updateTotalWaterConsumedTodayTextView();
    }
    private void updateTotalWaterConsumedTodayTextView() {
        String message = "Water consumed today: " + (totalWaterConsumedToday / 1000) + " bottles";
        totalWaterConsumedTodayTextView.setText(message);
    }
    private void loadTotalWaterConsumedToday() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        totalWaterConsumedToday = preferences.getInt(KEY_CONSUMED_AMOUNT_TODAY, 0);
        updateTotalWaterConsumedTodayTextView();
    }

    private boolean isDateRecorded(String date) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ExerciseDatabaseHelper.TABLE_WATER_CONSUMPTION,
                new String[]{ExerciseDatabaseHelper.COLUMN_DATE},
                ExerciseDatabaseHelper.COLUMN_DATE + " = ?",
                new String[]{date}, null, null, null);

        boolean isRecorded = cursor.getCount() > 0;

        cursor.close();
        return isRecorded;
    }

    private void insertWaterRecord(String date, int consumption) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ExerciseDatabaseHelper.COLUMN_DATE, date);
        values.put(ExerciseDatabaseHelper.COLUMN_CONSUMPTION, consumption);
        db.insert(ExerciseDatabaseHelper.TABLE_WATER_CONSUMPTION, null, values);
    }

    private void updateWaterRecord(String date, int consumption) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ExerciseDatabaseHelper.COLUMN_CONSUMPTION, consumption);
        db.update(ExerciseDatabaseHelper.TABLE_WATER_CONSUMPTION,
                values,
                ExerciseDatabaseHelper.COLUMN_DATE + " = ?",
                new String[]{date});
    }
    private void redirectToRedirectPage() {
        Intent intent = new Intent(this, RedirectPage.class);
        startActivity(intent);
        finish();
    }

    private void displayTotalExerciseDetails(int totalTimeSpent, int totalCaloriesBurned) {
        TextView totalExerciseTextView = findViewById(R.id.totalExerciseDetailsTextView);
        String exerciseDetailsMessage = "Total time spent on exercise: " + totalTimeSpent + " minutes\n" +
                "Total calories burned today: " + totalCaloriesBurned + " kcal";
        totalExerciseTextView.setText(exerciseDetailsMessage);
    }

    private void openEmergencyPage() {
        Intent intent = new Intent(this, EmergencyPageActivity.class);
        startActivity(intent);
    }
}

