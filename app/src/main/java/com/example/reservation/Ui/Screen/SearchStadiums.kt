package com.example.reservation.Ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reservation.Ui.Components.AppBottomNavigationBar
import com.example.reservation.Ui.Components.FieldCard
import com.example.reservation.ViewModel.HomeViewModel

@Composable
fun SearchStadiums(
    viewModel: HomeViewModel = viewModel(),
    onNavigate: (String) -> Unit = {}
) {
    val fields by viewModel.filteredFields.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    
    val primaryGreen = Color(0xFF00A669)

    Scaffold(
        bottomBar = { 
            AppBottomNavigationBar(
                currentRoute = "search",
                onNavigate = onNavigate
            ) 
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            // Search Header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Rechercher un terrain",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { viewModel.onSearchQueryChange(it) },
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .background(Color(0xFFF3F4F6), RoundedCornerShape(12.dp)),
                            placeholder = { Text("Nom du stade, ville...", color = Color.Gray) },
                            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null, tint = Color.Gray) },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent
                            )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        IconButton(
                            onClick = { /* Open Filters */ },
                            modifier = Modifier
                                .size(56.dp)
                                .background(Color(0xFFF3F4F6), RoundedCornerShape(12.dp))
                        ) {
                            Icon(Icons.Default.FilterList, contentDescription = null, tint = Color(0xFF4B5563))
                        }
                    }
                }
            }

            // Results Summary
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${fields.size} terrains trouvés",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF4B5563)
                )
                Text(
                    text = "Trier par : Pertinence",
                    fontSize = 14.sp,
                    color = primaryGreen,
                    fontWeight = FontWeight.Bold
                )
            }

            // Results List
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(fields) { field ->
                    FieldCard(
                        field = field,
                        onClick = { /* Navigate to Details */ }
                    )
                }
            }
        }
    }
}
