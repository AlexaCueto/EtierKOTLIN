package com.example.etierkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.etier.database.RentalDbHelper

class MainActivity : AppCompatActivity() {

    private lateinit var buttonAddRental: Button
    private lateinit var buttonViewRentals: Button
    private lateinit var buttonReports: Button
    private lateinit var buttonUpdateRental: Button
    private lateinit var buttonDeleteRental: Button

    private lateinit var dbHelper: RentalDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = RentalDbHelper(this)

        buttonAddRental = findViewById(R.id.buttonAddRental)
        buttonViewRentals = findViewById(R.id.buttonViewRentals)
        buttonReports = findViewById(R.id.buttonReports)
        buttonUpdateRental = findViewById(R.id.buttonUpdateRental)
        buttonDeleteRental = findViewById(R.id.buttonDeleteRental)

        //Disable buttons before login
        toggleButtons(false)

        //Show Login Dialog
        val loginDialog = LoginDialog {
            toggleButtons(true)
        }
        loginDialog.isCancelable = false
        loginDialog.show(supportFragmentManager, "loginDialog")

        setupButtons()
    }

    private fun toggleButtons(isEnabled: Boolean) {
        buttonAddRental.isEnabled = isEnabled
        buttonViewRentals.isEnabled = isEnabled
        buttonReports.isEnabled = isEnabled
        buttonUpdateRental.isEnabled = isEnabled
        buttonDeleteRental.isEnabled = isEnabled
    }

    private fun setupButtons() {
        buttonAddRental.setOnClickListener {
            startActivity(Intent(this, AddRentalActivity::class.java))
        }

        buttonViewRentals.setOnClickListener {
            startActivity(Intent(this, ViewRentalsActivity::class.java))
        }

        buttonReports.setOnClickListener {
            startActivity(Intent(this, ReportsActivity::class.java))
        }

        buttonUpdateRental.setOnClickListener {
            startActivity(Intent(this, UpdateRentalActivity::class.java))
        }

        buttonDeleteRental.setOnClickListener {
            startActivity(Intent(this, DeleteRentalsActivity::class.java))
        }
    }
}
