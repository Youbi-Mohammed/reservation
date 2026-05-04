package com.example.reservation.Ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reservation.ViewModel.UserInfosViewModel

@Composable
fun UserInfosScreen(
    viewModel: UserInfosViewModel = viewModel(),
    onContinue: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    val primaryGreen = Color(0xFF00A669)
    val lightGray = Color(0xFFF7F8F9)
    val labelColor = Color(0xFF4B5563)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Green Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(primaryGreen)
                .padding(top = 48.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { /* Handle back */ }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Retour",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Bienvenue !",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Complétez votre profil pour continuer",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 16.sp
                )
            }
        }

        // Form Fields
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            InfoTextField(
                label = "Prénom",
                value = uiState.firstName,
                onValueChange = { viewModel.updateFirstName(it) },
                placeholder = "Votre prénom",
                icon = Icons.Default.Person
            )

            Spacer(modifier = Modifier.height(16.dp))

            InfoTextField(
                label = "Nom",
                value = uiState.lastName,
                onValueChange = { viewModel.updateLastName(it) },
                placeholder = "Votre nom",
                icon = Icons.Default.Person
            )

            Spacer(modifier = Modifier.height(16.dp))

            InfoTextField(
                label = "Téléphone",
                value = uiState.phone,
                onValueChange = { viewModel.updatePhone(it) },
                placeholder = "+212 6XX XXX XXX",
                icon = Icons.Default.Phone
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Genre",
                color = labelColor,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                GenderCard(
                    label = "Homme",
                    emoji = "👦",
                    isSelected = uiState.gender == "Homme",
                    onClick = { viewModel.updateGender("Homme") },
                    modifier = Modifier.weight(1f),
                    primaryColor = primaryGreen
                )
                GenderCard(
                    label = "Femme",
                    emoji = "👧",
                    isSelected = uiState.gender == "Femme",
                    onClick = { viewModel.updateGender("Femme") },
                    modifier = Modifier.weight(1f),
                    primaryColor = primaryGreen
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            InfoTextField(
                label = "Date de naissance",
                value = uiState.birthDate,
                onValueChange = { viewModel.updateBirthDate(it) },
                placeholder = "JJ/MM/AAAA",
                icon = Icons.Default.CalendarToday
            )

            Spacer(modifier = Modifier.height(32.dp))

            val isFormComplete = uiState.firstName.isNotBlank() && 
                               uiState.lastName.isNotBlank() && 
                               uiState.phone.isNotBlank() && 
                               uiState.birthDate.length == 10

            Button(
                onClick = { 
                    viewModel.onContinue()
                    onContinue(uiState.firstName)
                },
                enabled = isFormComplete,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isFormComplete) primaryGreen else Color(0xFFD1D5DB)
                )
            ) {
                Text(
                    text = "Continuer",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun InfoTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector
) {
    Column {
        // Reuse original logic or keep simple version
        Text(
            text = label,
            color = Color(0xFF4B5563),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF7F8F9), RoundedCornerShape(16.dp)),
            placeholder = { Text(text = placeholder, color = Color.Gray) },
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            singleLine = true
        )
    }
}

@Composable
fun GenderCard(
    label: String,
    emoji: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    primaryColor: Color
) {
    Box(
        modifier = modifier
            .height(100.dp)
            .background(
                if (isSelected) Color.White else Color(0xFFF7F8F9),
                RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) primaryColor else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = emoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                color = if (isSelected) primaryColor else Color(0xFF4B5563),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserInfosScreenPreview() {
    UserInfosScreen()
}
