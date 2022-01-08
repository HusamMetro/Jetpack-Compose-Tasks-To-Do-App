package com.tuwaiq.husam.taskstodoapp.data.models

import androidx.compose.ui.graphics.Color
import com.tuwaiq.husam.taskstodoapp.ui.theme.HighPriorityColor
import com.tuwaiq.husam.taskstodoapp.ui.theme.LowPriorityColor
import com.tuwaiq.husam.taskstodoapp.ui.theme.MediumPriorityColor
import com.tuwaiq.husam.taskstodoapp.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}