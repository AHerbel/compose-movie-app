package com.aherbel.movieapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.aherbel.movieapp.presentation.theme.black
import com.aherbel.movieapp.presentation.theme.yellow

@Composable
fun Score(score: Float) {
    check(score in 0.0f..10f) {
        "Score must be a number between 0 and 10"
    }
    Clasification(
        buildAnnotatedString {
            val spanStyle = SpanStyle(color = black, fontWeight = FontWeight.ExtraBold)
            withStyle(style = spanStyle.copy(fontSize = 12.sp)) {
                append("$score")
            }
            withStyle(style = spanStyle.copy(fontSize = 8.sp)) {
                append("/10")
            }
        },
        backgroundColor = yellow
    )
}