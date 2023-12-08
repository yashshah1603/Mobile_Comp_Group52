package com.example.p4_record_emergency_contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private var recorder: MediaRecorder? = null
    private lateinit var fileName: String
    private val recordAudioRequestCode = 101
    private lateinit var dbh: DbHelper
    val tableLayout = findViewById<TableLayout>(R.id.table)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recordButton: Button = findViewById(R.id.recordButton)
        val saveButton: Button = findViewById(R.id.saveButton)

        recordButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    recordAudioRequestCode)
            } else {
                startRecording()
            }
        }

        saveButton.setOnClickListener {
            dbh = DbHelper(this)
        }
    }

    private fun startRecording() {
        fileName = "${externalCacheDir?.absolutePath}/audiorecordtest.3gp"

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e("AudioRecordTest", "prepare() failed")
            }

            start()
        }

        Handler().postDelayed({ stopRecording() }, 30000)
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

    private fun insertdata() {
        dbh.deleteAll()
        for (i in 0 until tableLayout.childCount) {
            val row = tableLayout.getChildAt(i) as? TableRow
            row?.let {
                val namecell = it.getChildAt(0) as? TextView
                val numbercell = it.getChildAt(1) as? TextView
                val emailcell = it.getChildAt(2) as? TextView
                val relationcell = it.getChildAt(3) as? TextView

                val name = namecell?.text.toString()
                val number = numbercell?.text.toString()
                val email = emailcell?.text.toString()
                val relation = relationcell?.text.toString()

                dbh.add(name, email, number, relation)
            }
        }
    }

    private fun readdata() {
        val cursor = dbh.getAll()
        val numberOfRowsToIterate = 5
        var currentRow = 0
        if (cursor.moveToFirst()) {
            do {
                val ni = cursor.getColumnIndex(DbHelper.COLUMN_NAME)
                if (ni != -1) {
                    val name = cursor.getString(ni)
                }
                val em = cursor.getColumnIndex(DbHelper.COLUMN_EMAIL)
                if (em != -1) {
                    val name = cursor.getString(em)
                }
                val nu = cursor.getColumnIndex(DbHelper.COLUMN_NUMBER)
                if (nu != -1) {
                    val number = cursor.getString(nu)
                }
                val ri = cursor.getColumnIndex(DbHelper.COLUMN_RELATION)
                if (ri != -1) {
                    val relation = cursor.getString(ri)
                }

                currentRow++
            } while (cursor.moveToNext() && currentRow < numberOfRowsToIterate)
        }
        cursor.close()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == recordAudioRequestCode && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startRecording()
        }
    }
}