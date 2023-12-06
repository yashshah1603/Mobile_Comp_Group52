package com.example.myloginapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
public class SymptomDetailsDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "rating_database";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "ratings";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String NAUSEA = "Nausea";
    public static final String HEADACHE = "Headache";
    public static final String DIARRHEA = "Diarrhea";
    public static final String SOAR_THROAT = "Soar_Throat";
    public static final String FEVER = "Fever";
    public static final String MUSCLE_ACHE = "Muscle_Ache";
    public static final String LOSS_OF_SMELL_OR_TASTE = "Loss_Of_Smell_Or_Taste";
    public static final String SHORTNESS_OF_BREATH = "Shortness_Of_Breath";
    public static final String FEELING_TIRED = "Feeling_Tired";
    public static final String COUGH = "Cough";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_TIMESTAMP + " TIMESTAMP PRIMARY KEY, " + NAUSEA + " REAL, " +
                    HEADACHE + " REAL, " + DIARRHEA + " REAL, " + SOAR_THROAT + " REAL, " +
                    FEVER + " REAL, " + MUSCLE_ACHE + " REAL, " + LOSS_OF_SMELL_OR_TASTE + " REAL, " +
                    SHORTNESS_OF_BREATH + " REAL, " + FEELING_TIRED + " REAL, " + COUGH + " REAL" + ")";


    public SymptomDetailsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertData(Map<String, Float> symptomsMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String currentTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
        values.put(COLUMN_TIMESTAMP, currentTimeStamp);
        for (Map.Entry<String, Float> entry : symptomsMap.entrySet()) {
            String columnName = entry.getKey();
            Float value = entry.getValue();
            if("Soar Throat".equals(columnName)){
                columnName = SOAR_THROAT;
            }
            else if("Muscle Ache".equals(columnName)){
                columnName = MUSCLE_ACHE;
            }
            else if("Loss of Smell or Taste".equals(columnName)){
                columnName = LOSS_OF_SMELL_OR_TASTE;
            }
            else if("Shortness of Breath".equals(columnName)){
                columnName = SHORTNESS_OF_BREATH;
            }
            else if("Feeling Tired".equals(columnName)){
                columnName = FEELING_TIRED;
            }
            if(!"Please select a symptom".equals(columnName)) {
                values.put(columnName, value);
            }
        }

        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    public Cursor queryAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public void clearOldData() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
