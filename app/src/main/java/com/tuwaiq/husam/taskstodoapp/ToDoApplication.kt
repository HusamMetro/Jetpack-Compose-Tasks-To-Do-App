package com.tuwaiq.husam.taskstodoapp

import android.app.Application
import android.util.Log

class ToDoApplication : Application() {
    init {
        Log.e("app", "Application Works before ")
    }
}