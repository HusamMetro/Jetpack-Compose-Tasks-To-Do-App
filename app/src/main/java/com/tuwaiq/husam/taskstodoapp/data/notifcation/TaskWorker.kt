package com.tuwaiq.husam.taskstodoapp.data.notifcation

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.tuwaiq.husam.taskstodoapp.MainActivity
@ExperimentalMaterialApi
@ExperimentalAnimationApi
class TaskWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val name = "To Do App"
        val intent = Intent(context, MainActivity::class.java)
        val pendingActivity =
            PendingIntent.getActivity(context, 3, intent, PendingIntent.FLAG_IMMUTABLE)


        val notification = NotificationCompat
            .Builder(context, "NOTIFICATION_CHANNEL_ID")
            .setTicker(name)
            .setSmallIcon(android.R.drawable.stat_notify_more)
            .setContentTitle(name)
            .setContentText("Check Out the Gold Challenges")
            .setContentIntent(pendingActivity) // new
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(0, notification)

        return Result.success()
    }

}