package com.example.etierkotlin

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin = findViewById(R.id.btn_login)

        buttonLogin.setOnClickListener {
            playMorphAnimation()
            val loginDialog = LoginDialog()
            loginDialog.isCancelable = false
            loginDialog.show(supportFragmentManager, "LoginDialog")
        }
    }

    private fun playMorphAnimation() {
        buttonLogin.animate()
            .scaleX(0.9f)
            .scaleY(0.9f)
            .alpha(0.7f)
            .setDuration(150)
            .withEndAction {
                buttonLogin.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .alpha(1f)
                    .setDuration(150)
                    .start()
            }
            .start()
    }
}
