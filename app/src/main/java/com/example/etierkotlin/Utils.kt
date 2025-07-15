package com.example.etierkotlin.utils

object Utils {
    fun getImageNameForItem(itemName: String): String {
        return when (itemName) {
            "Maxi Dress 1" -> "maxi_dress_1.jpg"
            "Maxi Dress 2" -> "maxi_dress_2.jpg"
            "Maxi Dress 3" -> "maxi_dress_3.jpg"
            "Maxi Dress 4" -> "maxi_dress_4.jpg"
            "Formal Gown 1" -> "formal_gown_1.jpg"
            "Formal Gown 2" -> "formal_gown_2.jpg"
            "Formal Gown 3" -> "formal_gown_3.jpg"
            "Formal Gown 4" -> "formal_gown_4.jpg"
            else -> "ic_default_image"
        }
    }
}