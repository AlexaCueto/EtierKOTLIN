package com.example.etierkotlin.utils

import com.example.etierkotlin.R

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
    fun getAvailableItemsForCategory(category: String): List<String> {
        return when (category) {
            "Maxi Dress" -> listOf(
                "Off-Shoulder Sakura Maxi Dress",
                "Hibiscus Dream Ruffled Midi",
                "Royal Blue Tiered Cami Maxi",
                "Lemon Drop Strapless Maxi"
            )
            "Formal Gown" -> listOf(
                "Classic Black Halter Neck Gown",
                "Champagne Sequin Gown",
                "Rose Gold Sequin Sparkle Gown",
                "Fuchsia One-Shoulder A-Line Overlay Gown"
            )
            else -> listOf()
        }
    }

    fun getImageResForItem(itemName: String): Int {
        val imageName = getImageNameForItem(itemName)
        return when (imageName) {
            "maxi_dress_1" -> R.drawable.maxi_dress_1
            "maxi_dress_2" -> R.drawable.maxi_dress_2
            "maxi_dress_3" -> R.drawable.maxi_dress_3
            "maxi_dress_4" -> R.drawable.maxi_dress_4
            "formal_gown_1" -> R.drawable.formal_gown_1
            "formal_gown_2" -> R.drawable.formal_gown_2
            "formal_gown_3" -> R.drawable.formal_gown_3
            "formal_gown_4" -> R.drawable.formal_gown_4
            else -> R.drawable.etier_logo_transp
        }
    }

}