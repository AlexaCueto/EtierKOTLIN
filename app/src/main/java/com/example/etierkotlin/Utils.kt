package com.example.etierkotlin.utils

object Utils {
    fun getImageNameForItem(itemName: String): String {
        return when (itemName) {
            "Off-Shoulder Sakura Maxi Dress" -> "maxi_dress_1"
            "Hibiscus Dream Ruffled Midi" -> "maxi_dress_2"
            "Royal Blue Tiered Cami Maxi" -> "maxi_dress_3"
            "Lemon Drop Strapless Maxi" -> "maxi_dress_4"
            "Classic Black Halter Neck Gown" -> "formal_gown_1"
            "Champagne Sequin Gown" -> "formal_gown_2"
            "Rose Gold Sequin Sparkle Gown" -> "formal_gown_3"
            "Fuchsia One-Shoulder A-Line Overlay Gown" -> "formal_gown_4"
            else -> "ic_default_image"
        }
    }
}