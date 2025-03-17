package com.damazo.featurecities.ui.screen

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.damazo.featurecities.R

@Composable
fun DownloadWarningDialog(
    onConfirmation: () -> Unit,
    onCancel: () -> Unit,
) {
    AlertDialog(
        icon = {
            Icon(
                modifier = Modifier.size(50.dp),
                imageVector = Icons.Default.Warning,
                contentDescription = stringResource(R.string.cities_download_button_description),
                tint = Color.Yellow
            )
        },
        title = {
            Text(stringResource(R.string.download_alert_title))
        },
        text = {
            Text(stringResource(R.string.download_alert_description))
        },
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = onConfirmation) {
                Text(stringResource(R.string.alert_continue))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancel
            ) {
                Text(stringResource(R.string.alert_cancel))
            }
        }
    )
}