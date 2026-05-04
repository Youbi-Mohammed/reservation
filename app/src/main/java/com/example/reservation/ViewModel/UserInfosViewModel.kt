package com.example.reservation.ViewModel

import androidx.lifecycle.ViewModel
import com.example.reservation.data.Model.User
import com.example.reservation.data.Repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserInfosViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(User())
    val uiState: StateFlow<User> = _uiState.asStateFlow()

    fun updateFirstName(firstName: String) {
        _uiState.update { it.copy(firstName = firstName) }
    }

    fun updateLastName(lastName: String) {
        _uiState.update { it.copy(lastName = lastName) }
    }

    fun updatePhone(phone: String) {
        _uiState.update { it.copy(phone = phone) }
    }

    fun updateGender(gender: String) {
        _uiState.update { it.copy(gender = gender) }
    }

    fun updateBirthDate(input: String) {
        // Simple mask for DD/MM/YYYY
        val cleanInput = input.replace("/", "").take(8)
        val formattedInput = buildString {
            for (i in cleanInput.indices) {
                append(cleanInput[i])
                if ((i == 1 || i == 3) && i != cleanInput.lastIndex) {
                    append("/")
                }
            }
        }
        _uiState.update { it.copy(birthDate = formattedInput) }
    }

    fun onContinue() {
        val user = _uiState.value
        UserRepository.saveUser(user)
        println("User saved to repository: $user")
    }
}
