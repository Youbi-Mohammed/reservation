package com.example.reservation.data.Model

data class Field(
    val id: String,
    val name: String,
    val rating: Double,
    val reviewsCount: Int,
    val address: String,
    val pricePerHour: Int,
    val category: String, // e.g., "Football", "Padel"
    val imageUrl: String? = null,
    val isAvailableToday: Boolean = true
)
