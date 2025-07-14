package com.example.etierkotlin

enum class ApparelCategory (val displayName: String){
    MAXI_DRESS("Maxi Dress"),
    FORMAL_GOWN("Formal Gown");

    companion object {
        fun getAllCategories():List<String>{
            return values().map { it.displayName }
        }
        fun fromDisplayName(name:String):ApparelCategory?{
            return values().find { it.displayName == name }
        }
    }
}