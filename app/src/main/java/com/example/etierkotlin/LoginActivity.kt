package com.example.etierkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var buttonOpenLoginDialog: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonOpenLoginDialog = findViewById(R.id.btn_login)

        buttonOpenLoginDialog.setOnClickListener {
            val loginDialog = LoginDialog()
            loginDialog.isCancelable = false
            loginDialog.show(supportFragmentManager, "LoginDialog")
        }
    }
}
