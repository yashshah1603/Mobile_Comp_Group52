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
        sidePlankButton.setOnClickListener(v -> startActivity(new Intent(ExerciseActivity.this, SidePlankActivity.class)));

        lungesButton.setOnClickListener(v -> startActivity(new Intent(ExerciseActivity.this, LungesActivity.class)));

        highSteppingButton.setOnClickListener(v -> startActivity(new Intent(ExerciseActivity.this, HighSteppingActivity.class)));

        absCrunchesButton.setOnClickListener(v -> startActivity(new Intent(ExerciseActivity.this, AbsCrunchesActivity.class)));

        pushupsButton.setOnClickListener(v -> startActivity(new Intent(ExerciseActivity.this, PushupsActivity.class)));
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

    private boolean getMarkAsDoneValue() {
        SidePlankActivity sidePlankActivity = new SidePlankActivity();
        boolean sidePlankDone = sidePlankActivity.isMarkedAsDone();
        return  sidePlankDone;
    }
}

