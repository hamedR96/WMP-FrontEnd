package com.faircorp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class MainActivity : BasicActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val callRoom1 = findViewById<Button>(R.id.button4)
        callRoom1.setOnClickListener {
            val intent = Intent(this, WindowsActivity::class.java).apply {
                putExtra(WINDOW_NAME_PARAM, 1L)
            }
            startActivity(intent)
        }

        val callRoom2 = findViewById<Button>(R.id.button)
        callRoom2.setOnClickListener {
            val intent = Intent(this, WindowsActivity::class.java).apply {
                putExtra(WINDOW_NAME_PARAM, 2L)
            }
            startActivity(intent)
        }

        val callRoom3 = findViewById<Button>(R.id.button2)
        callRoom3.setOnClickListener {
            val intent = Intent(this, WindowsActivity::class.java).apply {
                putExtra(WINDOW_NAME_PARAM, 3L)
            }
            startActivity(intent)
        }

        val callRoom4 = findViewById<Button>(R.id.button3)
        callRoom4.setOnClickListener {
            val intent = Intent(this, WindowsActivity::class.java).apply {
                putExtra(WINDOW_NAME_PARAM, 4L)
            }
            startActivity(intent)
        }
    }
}