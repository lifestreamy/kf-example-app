package com.example.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    surfaceColor: Color = MaterialTheme.colorScheme.surface,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    borderColor: Color = MaterialTheme.colorScheme.primary
) {
    val visuals = snackbarData.visuals

    Surface(
        shape = MaterialTheme.shapes.medium,
        color = surfaceColor,
        modifier = modifier.padding(5.dp),
        border = BorderStroke(
            5.dp,
            color = borderColor
        )
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = visuals.message,
                color = textColor
            )

        }
    }
}