package com.tuwaiq.husam.taskstodoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tuwaiq.husam.taskstodoapp.data.models.ToDoTask
import com.tuwaiq.husam.taskstodoapp.util.Constants.DATABASE_NAME

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract val toDoDao: ToDoDao

    companion object {
        private var INSTANCE: ToDoDatabase? = null
        fun getAppDatabase(context: Context): ToDoDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return INSTANCE
        }
    }
}