package com.example.reservation.data.Model

data class Reservation(
    val id: String,
    val stadiumName: String,
    val date: String,
    val time: String,
    val price: Int,
    val status: String, // e.g., "Confirmé", "En attente", "Annulé"
    val address: String,
    val imageUrl: String? = null
)
