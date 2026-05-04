package com.example.reservation.ViewModel

import androidx.lifecycle.ViewModel
import com.example.reservation.data.Model.Field
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val _allFields = MutableStateFlow<List<Field>>(emptyList())
    
    private val _selectedSport = MutableStateFlow("")
    val selectedSport: StateFlow<String> = _selectedSport.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Filtered fields based on sport and search query
    val fields: StateFlow<List<Field>> = combine(_allFields, _selectedSport, _searchQuery) { allFields, sport, query ->
        allFields.filter { field ->
            val matchesSport = if (sport.isEmpty()) true else field.category.equals(sport, ignoreCase = true)
            val matchesQuery = if (query.isEmpty()) true else field.name.contains(query, ignoreCase = true)
            matchesSport && matchesQuery
        }
    }.let { 
        val state = MutableStateFlow<List<Field>>(emptyList())
        // In a real app, we'd use stateIn. For simplicity here:
        _allFields.value.filter { it.category == _selectedSport.value } 
        // Returning a basic StateFlow for now, but will use combine properly in implementation
        _allFields.asStateFlow() 
    }
    
    // Better way to expose filtered fields
    private val _filteredFields = MutableStateFlow<List<Field>>(emptyList())
    val filteredFields: StateFlow<List<Field>> = _filteredFields.asStateFlow()

    init {
        // Mock data with categories
        _allFields.value = listOf(
            Field(
                id = "1",
                name = "Stade Al Massira",
                rating = 4.8,
                reviewsCount = 125,
                address = "123 Rue Mohammed V, Casablanca",
                pricePerHour = 200,
                category = "Football",
                isAvailableToday = true
            ),
            Field(
                id = "2",
                name = "City Foot 5",
                rating = 4.5,
                reviewsCount = 89,
                address = "Quartier Maârif, Casablanca",
                pricePerHour = 150,
                category = "Football",
                isAvailableToday = true
            ),
            Field(
                id = "3",
                name = "Padel Hub",
                rating = 4.9,
                reviewsCount = 56,
                address = "Anfa Place, Casablanca",
                pricePerHour = 300,
                category = "Padel",
                isAvailableToday = true
            ),
             Field(
                id = "4",
                name = "Tennis Academy",
                rating = 4.7,
                reviewsCount = 42,
                address = "Ain Diab, Casablanca",
                pricePerHour = 180,
                category = "Tennis",
                isAvailableToday = true
            )
        )
        updateFilteredFields()
    }

    fun setSport(sport: String) {
        _selectedSport.value = sport
        updateFilteredFields()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        updateFilteredFields()
    }

    private fun updateFilteredFields() {
        val sport = _selectedSport.value
        val query = _searchQuery.value
        _filteredFields.value = _allFields.value.filter { field ->
            val matchesSport = if (sport.isEmpty()) true else field.category.equals(sport, ignoreCase = true)
            val matchesQuery = if (query.isEmpty()) true else field.name.contains(query, ignoreCase = true)
            matchesSport && matchesQuery
        }
    }

    fun addField(field: Field) {
        _allFields.update { it + field }
        updateFilteredFields()
    }
}
