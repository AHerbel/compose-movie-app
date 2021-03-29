package com.aherbel.movieapp.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Line(
    color: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
    thickness: Dp = 1.dp,
) {
    val halfAlphaColor = color.copy(alpha = 0.5f)
    Box(modifier = Modifier
        .height(thickness)
        .fillMaxWidth(0.5f)
        .background(
            Brush.horizontalGradient(
                0.0f to halfAlphaColor,
                0.5f to color,
                1.0f to halfAlphaColor,
            )
        )
    )
}