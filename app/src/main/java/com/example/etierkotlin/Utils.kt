package com.example.etierkotlin.utils

object Utils {
    fun getImageNameForItem(itemName: String): String {
        return when (itemName) {
            "Off-Shoulder Sakura Maxi Dress" -> "maxi_dress_1.jpg"
            "Hibiscus Dream Ruffled Midi" -> "maxi_dress_2.jpg"
            "Royal Blue Tiered Cami Maxi" -> "maxi_dress_3.jpg"
            "Lemon Drop Strapless Maxi" -> "maxi_dress_4.jpg"
            "Classic Black Halter Neck Gown" -> "formal_gown_1.jpg"
            "Champagne Sequin Gown" -> "formal_gown_2.jpg"
            "Rose Gold Sequin Sparkle Gown" -> "formal_gown_3.jpg"
            "Fuchsia One-Shoulder A-Line Overlay Gown" -> "formal_gown_4.jpg"
            else -> "ic_default_image"
        }
    }
}