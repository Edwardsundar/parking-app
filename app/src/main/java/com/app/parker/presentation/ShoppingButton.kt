package com.app.parker.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingButton(modifier: Modifier = Modifier, onClick: () -> Unit, buttonText: String) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        onClick = onClick,
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(text = buttonText, style = MaterialTheme.typography.titleMedium)
    }
}