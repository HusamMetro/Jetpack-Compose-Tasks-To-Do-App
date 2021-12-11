package com.tuwaiq.husam.taskstodoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tuwaiq.husam.taskstodoapp.data.models.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)

abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao

   /* companion object {
        private var INSTANCE: TaskDatabase? = null
        fun getAppDatabase(context: Context): TaskDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).build()
            }
            return INSTANCE
        }
    }*/
}