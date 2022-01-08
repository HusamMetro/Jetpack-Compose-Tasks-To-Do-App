package com.tuwaiq.husam.taskstodoapp

import android.app.Application
import android.content.Context
import android.util.Log

class ToDoApplication : Application() {


    init {
        Log.e("app", "Application Works before ")
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
        fun getToDoContext(): Context {
            return appContext
        }
    }

    /*override fun onCreate() {
        super.onCreate()
//        appSharedPreferences = this.getSharedPreferences("stockSharedPreference", MODE_PRIVATE)

        // create notification
        val name = "Stock Notification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("NOTIFICATION_CHANNEL_ID", name, importance)
        val notificationManager: NotificationManager =
            getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }*/
}