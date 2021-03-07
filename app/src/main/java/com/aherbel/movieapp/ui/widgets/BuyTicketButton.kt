package com.aherbel.movieapp.ui.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aherbel.movieapp.R
import com.aherbel.movieapp.ui.theme.red
import java.util.*

@Composable
fun BuyTicketButton() {
    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(backgroundColor = red),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = stringResource(R.string.buy_ticket).toUpperCase(Locale.ROOT),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}