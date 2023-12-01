package com.example.p4_med_hist


import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val dbHelper = DatabaseHelper(this)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val a = findViewById<CheckBox>(R.id.bp)
        val av = if (a.isChecked) 1 else 0
        val b = findViewById<CheckBox>(R.id.arthritis)
        val bv = if (b.isChecked) 1 else 0
        val c = findViewById<CheckBox>(R.id.herpes)
        val cv = if (c.isChecked) 1 else 0
        val d = findViewById<CheckBox>(R.id.hiv)
        val dv = if (d.isChecked) 1 else 0
        val e = findViewById<CheckBox>(R.id.hormone)
        val ev = if (e.isChecked) 1 else 0
        val f = findViewById<CheckBox>(R.id.thyroid)
        val fv = if (f.isChecked) 1 else 0

        val values = ContentValues().apply {
            put("bp", av.toString())
            put("arthritis", bv.toString())
            put("herpes", cv.toString())
            put("hiv", dv.toString())
            put("hormone", ev.toString())
            put("thyroid", fv.toString())
        }
        val newRowId = db.insert("UserInfo", null, values)
        dbHelper.close()

    }
}