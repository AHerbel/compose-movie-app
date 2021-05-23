package com.aherbel.movieapp.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.aherbel.movieapp.R
import com.aherbel.movieapp.presentation.theme.jokerFont

@Composable
fun EmptyResults() {
    Column {
        Text(
            text = stringResource(R.string.no_movies_available),
            color = Color.White,
            fontSize = 50.sp,
            fontFamily = jokerFont,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}