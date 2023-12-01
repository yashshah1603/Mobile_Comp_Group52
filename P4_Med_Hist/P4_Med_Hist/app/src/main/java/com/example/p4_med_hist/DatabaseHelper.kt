package com.example.p4_med_hist


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "userdb"

        private const val TABLE_NAME = "UserInfo"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_AGE = "age"
        private const val COLUMN_HEIGHT = "height"
        private const val COLUMN_WEIGHT = "weight"
        private const val COLUMN_a = "bp"
        private const val COLUMN_b = "arthritis"
        private const val COLUMN_c = "herpes"
        private const val COLUMN_d = "hiv"
        private const val COLUMN_f = "hormone"
        private const val COLUMN_g = "thyroid"
    }

    private val CREATE_TABLE = (
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_EMAIL TEXT PRIMARY KEY, " +
                    "$COLUMN_NAME TEXT, " +
                    "$COLUMN_AGE TEXT, " +
                    "$COLUMN_HEIGHT TEXT, " +
                    "$COLUMN_WEIGHT TEXT, " +
                    "$COLUMN_a TEXT, " +
                    "$COLUMN_b TEXT, " +
                    "$COLUMN_c TEXT, " +
                    "$COLUMN_d TEXT, " +
                    "$COLUMN_f TEXT, " +
                    "$COLUMN_g TEXT,)"
            )

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}