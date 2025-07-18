package com.example.etierkotlin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.etier.database.RentalDbHelper
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AlertDialog

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


        val toolbar: Toolbar = findViewById(R.id.mainToolBar)
        setSupportActionBar(toolbar)

        dbHelper = RentalDbHelper(this)

        buttonAddRental = findViewById(R.id.buttonAddRental)
        buttonViewRentals = findViewById(R.id.buttonViewRentals)
        buttonUpdateRental = findViewById(R.id.buttonUpdateRental)
        buttonDeleteRental = findViewById(R.id.buttonDeleteRental)
        buttonReports = findViewById(R.id.buttonReports)

        setupButtons()
    }

    private fun setupButtons() {
        buttonAddRental.setOnClickListener {
            startActivity(Intent(this, AddRentalActivity::class.java))
        }

        buttonViewRentals.setOnClickListener {
            startActivity(Intent(this, ViewRentalsActivity::class.java))
        }

        buttonUpdateRental.setOnClickListener {
            startActivity(Intent(this, UpdateRentalActivity::class.java))
        }

        buttonDeleteRental.setOnClickListener {
            startActivity(Intent(this, DeleteRentalsActivity::class.java))
        }

        buttonReports.setOnClickListener {
            startActivity(Intent(this, ReportsActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }
            R.id.menu_exit -> {
                showExitConfirmationDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Exit App")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes") { _, _ -> finishAffinity() }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
