package com.aherbel.movieapp.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString

@Composable
fun Category(category: String) {
    Clasification(AnnotatedString(category))
}