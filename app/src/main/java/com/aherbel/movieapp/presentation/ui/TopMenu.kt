package com.aherbel.movieapp.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.aherbel.movieapp.R
import com.aherbel.movieapp.presentation.theme.roundedCornerShape

@Composable
fun TopMenu(
    text: String,
    onTextChange: (String) -> Unit,
) {
    Row(Modifier.padding(24.dp)) {
        val border = Modifier.border(
            BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
            roundedCornerShape
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_menu),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .height(56.dp)
                .then(border)
                .padding(10.dp)
        )
        
        Spacer(Modifier.width(8.dp))
        
        SearchField(text, onTextChange)
    }
}