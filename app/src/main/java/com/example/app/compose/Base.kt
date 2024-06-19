package com.example.app.compose


import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.app.R

class PlaceholderTransformation(private val placeholder: String, val color: Color) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return PlaceholderFilter(placeholder, color)
    }
}

fun PlaceholderFilter(placeholder: String, color: Color): TransformedText {
    val annotatedString = buildAnnotatedString {
        pushStyle(
            SpanStyle(color = color, fontSize = 16.sp)
        )

        append(placeholder)

        pop()
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return 0
        }

        override fun transformedToOriginal(offset: Int): Int {
            return 0
        }
    }

    return TransformedText(annotatedString, numberOffsetTranslator)
}

object RippleCustomTheme: RippleTheme {
    @Composable
    override fun defaultColor() =
        RippleTheme.defaultRippleColor(
            contentColor = colorResource(id = R.color.colorPrimaryDark),
            lightTheme = true
        )

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        contentColor = Color.White,
        lightTheme = true
    )
}

