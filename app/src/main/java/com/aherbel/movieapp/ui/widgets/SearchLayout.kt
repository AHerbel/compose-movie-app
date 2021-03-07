package com.aherbel.movieapp.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.aherbel.movieapp.R
import com.aherbel.movieapp.ui.theme.roundedCornerShape

@Composable
fun SearchLayout(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                roundedCornerShape
            )
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.search_products),
                    color = Color.White,
                )
            },
            textStyle = TextStyle.Default.copy(color = Color.White),
        )
        Spacer(Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(16.dp))
    }
}