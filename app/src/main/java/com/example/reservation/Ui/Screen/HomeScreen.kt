package com.example.reservation.Ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reservation.Ui.Components.AppBottomNavigationBar
import com.example.reservation.Ui.Components.FieldCard
import com.example.reservation.ViewModel.HomeViewModel
import com.example.reservation.data.Repository.UserRepository

@Composable
fun HomeScreen(
    userName: String = "mohammed",
    viewModel: HomeViewModel = viewModel(),
    onNavigate: (String) -> Unit = {}
) {
    val userState by UserRepository.user.collectAsState()
    val currentUserName = userState.firstName.ifEmpty { userName }
    
    val fields by viewModel.filteredFields.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    
    val primaryGreen = Color(0xFF00A669)

    Scaffold(
        bottomBar = { 
            AppBottomNavigationBar(
                currentRoute = "home",
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
            // Green Header with Search
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryGreen)
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Column {
                    Text(
                        text = "Bienvenue,",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 16.sp
                    )
                    Text(
                        text = currentUserName,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.onSearchQueryChange(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color.White, RoundedCornerShape(12.dp)),
                        placeholder = { Text("Rechercher un terrain...", color = Color.Gray) },
                        leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null, tint = Color.Gray) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent
                        )
                    )
                }
            }

            // Map Section Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(primaryGreen.copy(alpha = 0.1f), Color.White)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Mock Map Elements
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = primaryGreen,
                            modifier = Modifier.size(60.dp)
                        )
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(Color.White, CircleShape)
                                .border(2.dp, primaryGreen, CircleShape)
                        )
                    }
                    Text(
                        text = "Carte interactive",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4B5563)
                    )
                    Text(
                        text = "${fields.size} terrains trouvés",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .size(44.dp)
                        .background(Color.White, CircleShape)
                        .border(1.dp, Color(0xFFE5E7EB), CircleShape)
                        .clickable { /* Filter */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.FilterList, contentDescription = null, tint = Color(0xFF4B5563))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Terrains disponibles",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "${fields.size} résultats",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(fields) { field ->
                    FieldCard(field = field, onClick = { /* Details */ })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
