package com.example.reservation.data.Model

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "user@gmail.com",
    val phone: String = "",
    val gender: String = "Homme",
    val birthDate: String = ""
)
