package com.tuwaiq.husam.taskstodoapp.data.models

data class MockToDoTask(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.LOW,
    val startDate: String = "",
    val endDate: String = "",
    val maxTask: String = "1",
    val taskCounter: String = "0",
    val titleAR: String = "",
    val descriptionAR: String = "",
    val gold : Boolean = false
)
