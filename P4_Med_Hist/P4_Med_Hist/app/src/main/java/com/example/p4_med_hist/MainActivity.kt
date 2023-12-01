package com.example.p4_med_hist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    private lateinit var n: EditText
    private lateinit var a: EditText
    private lateinit var e: EditText
    private lateinit var h: EditText
    private lateinit var w: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        n = findViewById(R.id.name);
        a = findViewById(R.id.age);
        e = findViewById(R.id.email);
        h = findViewById(R.id.height);
        w = findViewById(R.id.weight);

        val nextPageButton: Button = findViewById(R.id.next)

        nextPageButton.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            intent.putExtra("n", n.toString())
            intent.putExtra("a", a.toString())
            intent.putExtra("e", e.toString())
            intent.putExtra("h", h.toString())
            intent.putExtra("w", w.toString())
            startActivity(intent)
        }
    }
}