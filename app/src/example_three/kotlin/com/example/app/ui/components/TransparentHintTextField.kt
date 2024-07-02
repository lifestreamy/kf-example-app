package com.example.app.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TransparentHintTextField(
    hint: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false
) {
    var fieldText by rememberSaveable { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(true) }
    val isHintVisible by remember(
        isFocused,
        fieldText
    ) { mutableStateOf(!isFocused and fieldText.isBlank()) }
    Box(
        modifier = modifier
            .border(
                2.dp,
                color = MaterialTheme.colorScheme.onBackground,
                RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
    ) {
        BasicTextField(
            value = fieldText,
            onValueChange = { newValue ->
                fieldText = newValue
                onValueChange(newValue)
            },
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isFocused = it.isFocused
                }
        )
        if (isHintVisible) {
            Text(
                modifier = Modifier.align(Alignment.TopStart),
                text = hint,
                style = textStyle,
                color = Color.DarkGray,
            )
        }
    }
}