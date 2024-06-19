package com.example.app.compose

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.R

@Composable
@Preview
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    font: FontFamily,
    isPassword: Boolean = false
) {
    val passwordVisibility = remember { mutableStateOf(false) }
    val isFocused = remember { mutableStateOf(false) }
    // Лейбл тут по идее нужен для отладки и не более. Добавил его что-бы не подсвечивало эти строки
    val borderColor = animateColorAsState(
        label = "BORDER_TEXT_FIELD",
        targetValue = if (isFocused.value) colorResource(id = R.color.border_focus) else colorResource(id = R.color.border)
    )

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Black, fontFamily = font, fontSize = 12.sp, letterSpacing = 0.5.sp) },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused.value = focusState.isFocused
            }
            .border(
                width = 1.dp,
                color = borderColor.value,
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Color.Black,
            focusedPlaceholderColor = colorResource(id = R.color.text50),
            unfocusedPlaceholderColor = colorResource(id = R.color.text50),

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        textStyle = TextStyle(
            color = colorResource(id = R.color.text_input),
            fontSize = 16.sp,
            fontFamily = font,
        ),
        visualTransformation = getVisualTransformation(value, passwordVisibility.value, placeholder, isPassword),
        trailingIcon = {
            if (isPassword) {
                val image = if (passwordVisibility.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                IconButton(
                    onClick = { passwordVisibility.value = !passwordVisibility.value },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = colorResource(id = R.color.light_light_gray)
                    )
                ) {
                    Icon(
                        imageVector = image,
                        contentDescription = null,
                        tint = colorResource(id = R.color.text)
                    )
                }
            }
        },
    )
}

@Composable
fun getVisualTransformation(
    value: String,
    passwordVisibility: Boolean,
    placeholder: String,
    isPassword: Boolean = false,
): VisualTransformation {
    return when {
        isPassword -> {
            when {
                value.isEmpty() -> PlaceholderTransformation(
                    placeholder,
                    colorResource(id = R.color.text50)
                )
                passwordVisibility -> VisualTransformation.None
                else -> PasswordVisualTransformation()
            }
        }
        !isPassword -> {
            if (value.isEmpty())
                PlaceholderTransformation(
                    placeholder,
                    colorResource(id = R.color.text50)
                )
            else VisualTransformation.None
        }
        else -> VisualTransformation.None
    }
}