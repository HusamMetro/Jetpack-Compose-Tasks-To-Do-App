package com.tuwaiq.husam.taskstodoapp.data.models

import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.ui.graphics.vector.ImageVector

enum class Mode(val imageVector: ImageVector) {
    Light(Icons.Filled.LightMode),
    Dark(Icons.Filled.DarkMode)
}