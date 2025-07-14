package com.example.etier.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.etier.model.Rental

class RentalDbHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VER) {

    companion object {
        private const val DB_NAME = "etier_rental.db"
        private const val DB_VER = 1
        private const val TB_NAME = "rentals"

        //column  names
        private const val COL_RENTER_ID = "renterId"
        private const val COL_ITEM_NAME = "itemName"
        private const val COL_CATEGORY = "category"
        private const val COL_FIRST_NAME = "renterFirstName"
        private const val COL_LAST_NAME = "renterLastName"
        private const val COL_RENTAL_DATE = "rentalDate"
        private const val COL_RETURN_DATE = "returnDate"
        private const val COL_PRICE = "price"
        private const val COL_STATUS = "status"
        private const val COL_NOTES = "notes"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = """
           
            )
        """.trimIndent()
        db.execSQL(query)
    }
}