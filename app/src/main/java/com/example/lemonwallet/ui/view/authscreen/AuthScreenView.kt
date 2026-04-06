package com.example.lemonwallet.ui.view.authscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthScreenView(
    onLoginSuccess: () -> Unit,
    onContinueAsGuest: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFEFCE8)) // Используем нежно-желтый фон, как на первой странице
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Верхняя часть: Иконка и текст в стиле стартовых страниц
        Spacer(modifier = Modifier.weight(0.4f))

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color(0xFFEAB308).copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🍋",
                fontSize = 64.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Lemon Wallet",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F2937),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Вход в ваш личный кошелек",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.weight(0.6f))

        // Нижняя часть: Кнопки авторизации
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthButton(
                text = "Войти через Google",
                containerColor = Color.White,
                contentColor = Color(0xFF1F2937),
                borderStroke = com.example.lemonwallet.ui.view.authscreen.BorderStroke(
                    1.dp,
                    Color.LightGray.copy(alpha = 0.5f)
                ),
                onClick = onLoginSuccess
            )
            AuthButton(
                text = "Войти с VK ID",
                containerColor = Color(0xFF0077FF),
                contentColor = Color.White,
                onClick = onLoginSuccess
            )

            AuthButton(
                text = "Войти с Яндекс ID",
                containerColor = Color(0xFFFC3F1D),
                contentColor = Color.White,
                onClick = onLoginSuccess
            )

            AuthButton(
                text = "По почте или номеру",
                containerColor = Color(0xFF111827), // Темная кнопка для контраста
                contentColor = Color.White,
                onClick = onLoginSuccess
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Кнопка "Продолжить без входа"
            TextButton(
                modifier = Modifier
                    .clickable { onContinueAsGuest() }
                    .padding(8.dp),
                onClick = onContinueAsGuest,
                ) {
                Text(
                    text = "Продолжить без входа",
                    fontSize = 14.sp,
                    color = Color(0xFF374151).copy(alpha = 0.7f),
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Medium
                )
            }

        }
    }
}

@Composable
fun AuthButton(
    text: String,
    containerColor: Color,
    contentColor: Color,
    borderStroke: com.example.lemonwallet.ui.view.authscreen.BorderStroke? = null,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = borderStroke?.let { BorderStroke(it.width, it.color) },
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

data class BorderStroke(val width: Dp, val color: Color)