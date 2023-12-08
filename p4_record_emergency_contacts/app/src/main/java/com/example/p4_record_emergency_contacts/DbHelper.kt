package com.example.p4_record_emergency_contacts

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableStatement = "CREATE TABLE $TABLE_NAME ($COLUMN_NUMBER TEXT PRIMARY KEY" +
                ", $COLUMN_NAME TEXT, $COLUMN_EMAIL TEXT, $COLUMN_RELATION TEXT)"
        db.execSQL(createTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun add(name: String, email: String, number: String, relation: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_NAME, name)
        cv.put(COLUMN_EMAIL, email)
        cv.put(COLUMN_NUMBER, number)
        cv.put(COLUMN_RELATION, relation)
        db.insert(TABLE_NAME, null, cv)
        db.close()
    }

    fun deleteAll(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
    }

    fun getAll(): Cursor {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        return db.rawQuery(query, null)
    }

    companion object {
        private const val DATABASE_NAME = "relation_database"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "relationships"
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "name"
        const val COLUMN_NUMBER = "number"
        const val COLUMN_RELATION = "relation"
    }
}
