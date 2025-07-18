package com.example.etierkotlin

import ItemSpinnerAdapter
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.etier.database.RentalDbHelper
import com.example.etierkotlin.model.Rental
import com.example.etierkotlin.model.SpinnerItem
import com.example.etierkotlin.utils.Utils
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    private lateinit var addressSuggestions: ArrayAdapter<String>
    private lateinit var retrofit: Retrofit
    private lateinit var locationIQApi: LocationIQApi
    private lateinit var autocompleteLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rental)

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
        val buttonBack = findViewById<Button>(R.id.buttonBack)

        dbHelper = RentalDbHelper(this)

        retrofit = Retrofit.Builder()
            .baseUrl("https://api.locationiq.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        locationIQApi = retrofit.create(LocationIQApi::class.java)

        addressSuggestions = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, mutableListOf())
        (editAddress as AutoCompleteTextView).setAdapter(addressSuggestions)

        setupCategorySpinner()
        setupStatusSpinner()
        setupDatePicker(editRentalDate)
        setupDatePicker(editReturnDate)
        setupAddressAutocomplete()
        setupAutocompleteLauncher()

        buttonSaveRental.setOnClickListener { saveRentalRecord() }
        editAddress.setOnClickListener { startAutocomplete() }
        buttonBack.setOnClickListener { finish() }
    }

    private fun setupCategorySpinner() {
        val categories = listOf("Maxi Dress", "Formal Gown")
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = categoryAdapter

        fun updateItemSpinner(selectedCategory: String) {
            val items = Utils.getAvailableItemsForCategory(selectedCategory)
            val spinnerItems = items.map { itemName ->
                SpinnerItem(itemName, Utils.getImageResForItem(itemName))
            }
            spinnerItemName.adapter = ItemSpinnerAdapter(this, spinnerItems)
        }

        //Initial load with first category
        updateItemSpinner(categories[0])

        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedCategory = categories[position]
                updateItemSpinner(selectedCategory)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
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
            DatePickerDialog(this, { _, y, m, d ->
                editText.setText(String.format("%04d-%02d-%02d", y, m + 1, d))
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun setupAddressAutocomplete() {
        editAddress.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()
                if (input.length >= 3) {
                    locationIQApi.getAutocomplete("pk.541d4664ed831aa3c3f789bbbcf20a41", input)
                        .enqueue(object : Callback<List<LocationIQPlace>> {
                            override fun onResponse(
                                call: Call<List<LocationIQPlace>>,
                                response: retrofit2.Response<List<LocationIQPlace>>
                            ) {
                                if (response.isSuccessful) {
                                    val places = response.body()?.map { it.display_name } ?: listOf()
                                    addressSuggestions.clear()
                                    addressSuggestions.addAll(places)
                                    addressSuggestions.notifyDataSetChanged()
                                }
                            }
                            override fun onFailure(call: Call<List<LocationIQPlace>>, t: Throwable) {
                                Toast.makeText(this@AddRentalActivity, "Failed to fetch addresses", Toast.LENGTH_SHORT).show()
                            }
                        })
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupAutocompleteLauncher() {
        autocompleteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(result.data!!)
                editAddress.setText(place.address)
            } else if (result.resultCode == AutocompleteActivity.RESULT_ERROR) {
                val status = Autocomplete.getStatusFromIntent(result.data!!)
                Toast.makeText(this, "Error: ${status.statusMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startAutocomplete() {
        val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
        autocompleteLauncher.launch(intent)
    }

    private fun saveRentalRecord() {
        val selectedSpinnerItem = spinnerItemName.selectedItem as SpinnerItem
        val selectedCategory = spinnerCategory.selectedItem.toString()

        val renterFirstName = editRenterFName.text.toString().trim()
        val renterLastName = editRenterLName.text.toString().trim()
        val rentalDate = editRentalDate.text.toString().trim()
        val returnDate = editReturnDate.text.toString().trim()
        val price = editPrice.text.toString().toDoubleOrNull()
        val status = spinnerStatus.selectedItem.toString()
        val notes = editNotes.text.toString().trim()
        val address = editAddress.text.toString().trim()

        if (selectedSpinnerItem.name.isNotEmpty() && renterFirstName.isNotEmpty() && renterLastName.isNotEmpty()
            && rentalDate.isNotEmpty() && returnDate.isNotEmpty() && price != null && address.isNotEmpty()
        ) {
            // Check if item is already rented
            if (dbHelper.isItemRented(selectedSpinnerItem.name)) {
                Toast.makeText(this, "${selectedSpinnerItem.name} is already rented out!", Toast.LENGTH_SHORT).show()
                return
            }

            val renterId = UUID.randomUUID().toString().take(8)

            val rental = Rental(
                renterId = renterId,
                itemName = selectedSpinnerItem.name,
                category = selectedCategory,
                renterFirstName = renterFirstName,
                renterLastName = renterLastName,
                renterAddress = address,
                rentalDate = rentalDate,
                returnDate = returnDate,
                price = price,
                status = status,
                notes = notes,
                imageName = Utils.getImageNameForItem(selectedSpinnerItem.name)
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
