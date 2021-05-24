package com.aherbel.movieapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun AlphaBackground(
    backgroundImage: Any?,
    color: Color,
    alpha: Float
) {
    Box(Modifier.fillMaxSize()) {
        Image(
            painter = rememberCoilPainter(request = backgroundImage),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = ColorPainter(color.copy(alpha = alpha)),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
        )
    }
}