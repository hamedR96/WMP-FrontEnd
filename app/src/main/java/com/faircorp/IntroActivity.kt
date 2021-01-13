package com.faircorp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setDecorFitsSystemWindows(false)
        setContentView(R.layout.activity_intro)

        Handler().postDelayed({
            startActivity(Intent(this@IntroActivity, MainActivity::class.java))
            finish()
        }, 3000)
    }
}
