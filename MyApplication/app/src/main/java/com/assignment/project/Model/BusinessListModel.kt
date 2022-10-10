package com.assignment.project.Model

data class BusinessListModel(
    val businesses: java.util.ArrayList<Businesse>,
    val region: Region,
    val total: Int
) {
    data class Businesse(
        val alias: String,
        val categories: java.util.ArrayList<Category>,
        val coordinates: Coordinates,
        val display_phone: String,
        val distance: Double,
        val id: String,
        val image_url: String,
        val is_closed: Boolean,
        val location: Location,
        val name: String,
        val phone: String,
        val price: String,
        val rating: Double,
        val review_count: Int,
        val transactions: java.util.ArrayList<String>,
        val url: String
    ) {
        data class Category(
            val alias: String,
            val title: String
        )

        data class Coordinates(
            val latitude: Double,
            val longitude: Double
        )

        data class Location(
            val address1: String,
            val address2: String,
            val address3: Any,
            val city: String,
            val country: String,
            val display_address: java.util.ArrayList<String>,
            val state: String,
            val zip_code: String
        )
    }

    data class Region(
        val center: Center
    ) {
        data class Center(
            val latitude: Double,
            val longitude: Double
        )
    }
}