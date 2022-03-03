package edu.upf.tickeep.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import edu.upf.tickeep.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.Theme_Tickeep)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonSignup: Button = findViewById(R.id.btn_signup)
        val buttonSignIn: Button = findViewById(R.id.btn_signin)
        buttonSignup.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
        }
        buttonSignIn.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }

}