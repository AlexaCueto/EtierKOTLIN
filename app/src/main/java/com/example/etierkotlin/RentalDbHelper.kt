package com.example.etier.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.etierkotlin.model.Rental

class RentalDbHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VER) {

    companion object {
        private const val DB_NAME = "etier_rental.db"
        private const val DB_VER = 3
        private const val TB_NAME = "rentals"

        //column  names
        private const val COL_RENTER_ID = "renterId"
        private const val COL_ITEM_NAME = "itemName"
        private const val COL_IMAGE_NAME = "imageName"
        private const val COL_CATEGORY = "category"
        private const val COL_FIRST_NAME = "renterFirstName"
        private const val COL_LAST_NAME = "renterLastName"
        private const val COL_ADDRESS = "renterAddress"
        private const val COL_RENTAL_DATE = "rentalDate"
        private const val COL_RETURN_DATE = "returnDate"
        private const val COL_PRICE = "price"
        private const val COL_STATUS = "status"
        private const val COL_NOTES = "notes"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = """
            CREATE TABLE $TB_NAME (
                $COL_RENTER_ID TEXT PRIMARY KEY,
                $COL_ITEM_NAME TEXT,
                $COL_IMAGE_NAME TEXT,
                $COL_CATEGORY TEXT,
                $COL_FIRST_NAME TEXT,
                $COL_LAST_NAME TEXT,
                $COL_ADDRESS TEXT,
                $COL_RENTAL_DATE TEXT,
                $COL_RETURN_DATE TEXT, 
                $COL_PRICE REAL,
                $COL_STATUS TEXT,
                $COL_NOTES TEXT
            )
        """.trimIndent()
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TB_NAME")
        onCreate(db)
    }

    //CREATE rental record
    fun addRental(rental: Rental): Boolean {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put(COL_RENTER_ID, rental.renterId)
            put(COL_ITEM_NAME, rental.itemName)
            put(COL_IMAGE_NAME, rental.imageName)
            put(COL_CATEGORY, rental.category)
            put(COL_FIRST_NAME, rental.renterFirstName)
            put(COL_LAST_NAME, rental.renterLastName)
            put(COL_ADDRESS, rental.renterAddress)
            put(COL_RENTAL_DATE, rental.rentalDate)
            put(COL_RETURN_DATE, rental.returnDate)
            put(COL_PRICE, rental.price)
            put(COL_STATUS, rental.status)
            put(COL_NOTES, rental.notes)
        }
        val result = db.insert(TB_NAME, null, cv)
        db.close()
        return result != -1L
    }

    //READ all rentals
    fun getAllRentals(): List<Rental> {
        val rentals = mutableListOf<Rental>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TB_NAME", null)

        while (cursor.moveToNext()) {
            val rental = Rental(
                renterId = cursor.getString(0),
                itemName = cursor.getString(1),
                imageName = cursor.getString(10),
                category = cursor.getString(2),
                renterFirstName = cursor.getString(3),
                renterLastName = cursor.getString(4),
                renterAddress = cursor.getString(6),
                rentalDate = cursor.getString(5),
                returnDate = cursor.getString(6),
                price = cursor.getDouble(7),
                status = cursor.getString(10),
                notes = cursor.getString(11)
            )
            rentals.add(rental)
        }
        cursor.close()
        db.close()
        return rentals
    }

    //UPDATE rental
    fun updateRental(rental: Rental): Boolean {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put(COL_ITEM_NAME, rental.itemName)
            put(COL_IMAGE_NAME, rental.imageName)
            put(COL_CATEGORY, rental.category)
            put(COL_FIRST_NAME, rental.renterFirstName)
            put(COL_LAST_NAME, rental.renterLastName)
            put(COL_ADDRESS, rental.renterAddress)
            put(COL_RENTAL_DATE, rental.rentalDate)
            put(COL_RETURN_DATE, rental.returnDate)
            put(COL_PRICE, rental.price)
            put(COL_STATUS, rental.status)
            put(COL_NOTES, rental.notes)
        }
        val result = db.update(TB_NAME, cv, "$COL_RENTER_ID = ?", arrayOf(rental.renterId))
        db.close()
        return result > 0
    }

    //DELETE rental
    fun deleteRental(renterId: String): Boolean {
        val db = writableDatabase
        val result = db.delete(TB_NAME, "$COL_RENTER_ID = ?", arrayOf(renterId))
        db.close()
        return result > 0
    }

    //REPORT
    //Total Rentals
    fun getTotalRentals(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TB_NAME", null)
        cursor.moveToFirst()
        val total = cursor.getInt(0)
        cursor.close()
        db.close()
        return total
    }

    //Total revenue
    fun getTotalRevenue(): Double {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT SUM($COL_PRICE) FROM $TB_NAME", null)
        cursor.moveToFirst()
        val total = cursor.getDouble(0)
        cursor.close()
        db.close()
        return total
    }

    //Most rented category
    fun getMostRentedCategory(): String {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COL_CATEGORY, COUNT(*) as count FROM $TB_NAME GROUP BY $COL_CATEGORY ORDER BY count DESC LIMIT 1",
            null
        )
        val topCategory = if (cursor.moveToFirst()) cursor.getString(0) else "None"
        cursor.close()
        db.close()
        return topCategory
    }

    //Check if an item is rented
    fun isItemRented(itemName: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TB_NAME WHERE $COL_ITEM_NAME = ? AND $COL_STATUS = ?",
            arrayOf(itemName, "Rented")
        )
        val isRented = cursor.moveToFirst()
        cursor.close()
        db.close()
        return isRented
    }
}