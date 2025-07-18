package com.example.etierkotlin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.etier.database.RentalDbHelper

class MainActivity : AppCompatActivity() {

    private lateinit var buttonAddRental: Button
    private lateinit var buttonViewRentals: Button
    private lateinit var buttonUpdateRental: Button
    private lateinit var buttonDeleteRental: Button
    private lateinit var buttonReports: Button

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

        setupButtonWithAnimation(buttonAddRental, AddRentalActivity::class.java)
        setupButtonWithAnimation(buttonViewRentals, ViewRentalsActivity::class.java)
        setupButtonWithAnimation(buttonUpdateRental, UpdateRentalActivity::class.java)
        setupButtonWithAnimation(buttonDeleteRental, DeleteRentalsActivity::class.java)
        setupButtonWithAnimation(buttonReports, ReportsActivity::class.java)
    }

    private fun setupButtonWithAnimation(button: Button, targetActivity: Class<*>) {
        button.setOnClickListener {
            button.animate()
                .scaleX(0.85f)
                .scaleY(0.85f)
                .alpha(0.7f)
                .setDuration(100)
                .withEndAction {
                    button.animate()
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .alpha(1f)
                        .setDuration(100)
                        .withEndAction {
                            button.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(50)
                                .withEndAction {
                                    startActivity(Intent(this, targetActivity))
                                }
                                .start()
                        }
                        .start()
                }
                .start()
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
            R.id.menu_logout -> {
                showLogoutConfirmationDialog()
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

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Log Out")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { _, _ ->
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
