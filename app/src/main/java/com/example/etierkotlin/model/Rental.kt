package com.example.etierkotlin.model

data class Rental(
    val renterId: Int,
    val itemName:String,
    val category:String,
    val renterFirstName:String,
    val renterLastName:String,
    val rentalDate:String,
    val returnDate:String,
    val price:Double,
    val status:String,
    val notes:String,
)
