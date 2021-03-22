package com.aherbel.movieapp.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aherbel.movieapp.R
import com.aherbel.movieapp.ui.theme.MovieAppTheme
import com.aherbel.movieapp.ui.theme.roundedCornerShape

@Composable
fun SearchLayout(state: SearchState) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                roundedCornerShape
            )
    ) {
        var text by remember { mutableStateOf(state.text) }
        TextField(
            value = text,
            onValueChange = {
                state.onValueChangeInternal(it)
                text = it
            },
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.White,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.search_products),
                    color = Color.White,
                )
            },
            textStyle = TextStyle.Default.copy(
                color = Color.White,
                textDecoration = TextDecoration.None,
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        )
    }
}

@Composable
fun rememberSearchState(initialValue: String = "", onValueChange: (String) -> Unit): SearchState {
    return remember { SearchState(initialValue, onValueChange) }
}

class SearchState constructor(
    initialValue: String,
    val onValueChange: (String) -> Unit
) {
    
    var text: String = initialValue
    
    internal fun onValueChangeInternal(newText: String) {
        text = newText
        this.onValueChange(newText)
    }
    
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_3A,
    backgroundColor = 0xFF29282C
)
@Composable
fun SearchLayoutPreview() {
    MovieAppTheme {
        val state = rememberSearchState(initialValue = "") {
        
        }
        SearchLayout(state)
    }
}