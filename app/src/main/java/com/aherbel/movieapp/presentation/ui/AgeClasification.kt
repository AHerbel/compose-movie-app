package com.aherbel.movieapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString

@Composable
fun AgeClasification(age: Int) {
    Clasification(AnnotatedString("$age+"))
}