package com.aherbel.movieapp.presentation.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun Descriptors(
    descriptors: List<String>,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 10.sp,
) {
    Text(
        text = descriptors.joinToString(separator = " \u00b7 "),
        color = Color.White,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}