package com.tuwaiq.husam.taskstodoapp.data.repositories

import android.content.Context
import com.tuwaiq.husam.taskstodoapp.data.ToDoDao
import com.tuwaiq.husam.taskstodoapp.data.ToDoDatabase
import com.tuwaiq.husam.taskstodoapp.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow

class ToDoRepository(context: Context) {
    private val toDoDao: ToDoDao = ToDoDatabase.getAppDatabase(context)!!.toDoDao

    val getAllTasks: Flow<List<ToDoTask>> = toDoDao.getAllTasks()
    val sortByLowPriority: Flow<List<ToDoTask>> = toDoDao.sortByLowPriority()
    val sortByHighPriority: Flow<List<ToDoTask>> = toDoDao.sortByHighPriority()

    fun getSelectedTask(taskId: Int): Flow<ToDoTask> {
        return toDoDao.getSelectedTask(taskId)
    }

    suspend fun addTask(toDoTask: ToDoTask) {
        toDoDao.addTask(toDoTask = toDoTask)
    }

    suspend fun updateTask(toDoTask: ToDoTask) {
        toDoDao.updateTask(toDoTask = toDoTask)
    }

    suspend fun deleteTask(toDoTask: ToDoTask) {
        toDoDao.deleteTask(toDoTask = toDoTask)
    }

    suspend fun deleteAllTasks() {
        toDoDao.deleteAllTasks()
    }

    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>> {
        return toDoDao.searchDatabase(searchQuery)
    }
}