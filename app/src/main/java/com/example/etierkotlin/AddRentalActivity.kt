package com.example.etierkotlin

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.etier.database.RentalDbHelper
import com.example.etierkotlin.model.Rental
import com.example.etierkotlin.utils.Utils
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.*

class AddRentalActivity : AppCompatActivity() {

    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerItemName: Spinner
    private lateinit var editRenterFName: EditText
    private lateinit var editRenterLName: EditText
    private lateinit var editRentalDate: EditText
    private lateinit var editReturnDate: EditText
    private lateinit var editPrice: EditText
    private lateinit var spinnerStatus: Spinner
    private lateinit var editNotes: EditText
    private lateinit var editAddress: EditText
    private lateinit var buttonSaveRental: Button

    private lateinit var dbHelper: RentalDbHelper

    private lateinit var autocompleteLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rental)

        dbHelper = RentalDbHelper(this)

        // Initialize Places API if not yet initialized
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "YOUR_API_KEY_HERE")
        }

        spinnerCategory = findViewById(R.id.spinnerCategory)
        spinnerItemName = findViewById(R.id.spinnerItemName)
        editRenterFName = findViewById(R.id.editRenterFName)
        editRenterLName = findViewById(R.id.editRenterLName)
        editRentalDate = findViewById(R.id.editRentalDate)
        editReturnDate = findViewById(R.id.editReturnDate)
        editPrice = findViewById(R.id.editPrice)
        spinnerStatus = findViewById(R.id.spinnerStatus)
        editNotes = findViewById(R.id.editNotes)
        editAddress = findViewById(R.id.editAddress)
        buttonSaveRental = findViewById(R.id.buttonSaveRental)

        setupCategorySpinner()
        setupStatusSpinner()
        setupDatePicker(editRentalDate)
        setupDatePicker(editReturnDate)

        buttonSaveRental.setOnClickListener {
            saveRentalRecord()
        }

        setupAutocompleteLauncher()

        editAddress.setOnClickListener {
            startAutocomplete()
        }
    }

    private fun setupCategorySpinner() {
        val categories = listOf("Maxi Dress", "Formal Gown")
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = categoryAdapter

        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedCategory = categories[position]
                setupItemNameSpinner(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupItemNameSpinner(category: String) {
        val items = when (category) {
            "Maxi Dress" -> listOf("Off-Shoulder Sakura Maxi Dress", "Hibiscus Dream Ruffled Midi", "Royal Blue Tiered Cami Maxi", "Lemon Drop Strapless Maxi")
            "Formal Gown" -> listOf("Classic Black Halter Neck Gown", "Champagne Sequin Gown", "Rose Gold Sequin Sparkle Gown", "Fuchsia One-Shoulder A-Line Overlay Gown")
            else -> listOf()
        }

        val itemAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerItemName.adapter = itemAdapter
    }

    private fun setupStatusSpinner() {
        val statuses = listOf("Rented", "Returned")
        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statuses)
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatus.adapter = statusAdapter
    }

    @SuppressLint("DefaultLocale")
    private fun setupDatePicker(editText: EditText) {
        editText.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this, { _, y, m, d ->
                val formattedDate = String.format("%04d-%02d-%02d", y, m + 1, d)
                editText.setText(formattedDate)
            }, year, month, day)
            dpd.show()
        }
    }

    //for the autocomplete service
    private fun setupAutocompleteLauncher() {
        autocompleteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(result.data!!)
                editAddress.setText(place.address)
            } else if (result.resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Autocomplete canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startAutocomplete() {
        val fields = listOf(Place.Field.ADDRESS)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .build(this)
        autocompleteLauncher.launch(intent)
    }

    private fun saveRentalRecord() {
        val itemName = spinnerItemName.selectedItem?.toString() ?: ""
        val selectedCategory = spinnerCategory.selectedItem.toString()
        val renterFirstName = editRenterFName.text.toString().trim()
        val renterLastName = editRenterLName.text.toString().trim()
        val rentalDate = editRentalDate.text.toString().trim()
        val returnDate = editReturnDate.text.toString().trim()
        val price = editPrice.text.toString().toDoubleOrNull()
        val status = spinnerStatus.selectedItem.toString()
        val notes = editNotes.text.toString().trim()
        val address = editAddress.text.toString().trim()

        if (itemName.isNotEmpty() && renterFirstName.isNotEmpty() && renterLastName.isNotEmpty()
            && rentalDate.isNotEmpty() && returnDate.isNotEmpty() && price != null && address.isNotEmpty()
        ) {
            val renterId = UUID.randomUUID().toString().take(8)
            val imageName = Utils.getImageNameForItem(itemName)

            val rental = Rental(
                renterId = renterId,
                itemName = itemName,
                category = selectedCategory,
                renterFirstName = renterFirstName,
                renterLastName = renterLastName,
                renterAddress = address,
                rentalDate = rentalDate,
                returnDate = returnDate,
                price = price,
                status = status,
                notes = notes,
                imageName = imageName
            )

            val success = dbHelper.addRental(rental)

            if (success) {
                Toast.makeText(this, "Rental record added!", Toast.LENGTH_SHORT).show()
                clearFields()
            } else {
                Toast.makeText(this, "Error adding record.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill out all required fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        spinnerCategory.setSelection(0)
        spinnerItemName.setSelection(0)
        editRenterFName.text.clear()
        editRenterLName.text.clear()
        editRentalDate.text.clear()
        editReturnDate.text.clear()
        editPrice.text.clear()
        editNotes.text.clear()
        editAddress.text.clear()
        spinnerStatus.setSelection(0)
    }
}