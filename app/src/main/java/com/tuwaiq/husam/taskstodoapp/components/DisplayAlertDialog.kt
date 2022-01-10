package com.tuwaiq.husam.taskstodoapp.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.ui.theme.alertButtonColor
import com.tuwaiq.husam.taskstodoapp.ui.theme.alertOutlinedButtonColor

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit,
) {
    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onYesClicked()
                        closeDialog()
                    },
                    colors = MaterialTheme.colors.alertButtonColor
                ) {
                    Text(text = stringResource(R.string.yes))
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        closeDialog()
                    },
                    colors = MaterialTheme.colors.alertOutlinedButtonColor
                ) {
                    Text(text = stringResource(R.string.no))
                }
            },
            onDismissRequest = { closeDialog() },
        )
    }

}