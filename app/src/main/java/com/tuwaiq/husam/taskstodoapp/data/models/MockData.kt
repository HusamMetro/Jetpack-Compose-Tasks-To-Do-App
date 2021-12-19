package com.tuwaiq.husam.taskstodoapp.data.models

data class MockToDoTask(
    val id: Int = 0,
    val title: String ="",
    val description: String="",
    val priority: Priority = Priority.LOW
)
