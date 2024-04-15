package com.app.parker.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.parker.R

@Composable
fun AlertDialogWithContent(
    title: String,
    content: String,
    drawableId: Int,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(text = title)
        },
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = drawableId),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = content)
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                },
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onCancel()
                },
            ) {
                Text(text = "Cancel")
            }
        }
    )
}
