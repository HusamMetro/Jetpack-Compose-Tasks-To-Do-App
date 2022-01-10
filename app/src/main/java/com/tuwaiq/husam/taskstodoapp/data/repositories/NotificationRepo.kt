package com.tuwaiq.husam.taskstodoapp.data.repositories

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.tuwaiq.husam.taskstodoapp.MainActivity
import com.tuwaiq.husam.taskstodoapp.data.notifcation.TaskWorker
import java.util.concurrent.TimeUnit

class NotificationRepo {
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    fun displayNotification(mainActivity: MainActivity) {
        //------------------------------
        val periodicWorker = PeriodicWorkRequest
            // minimum time 15 Minutes even if i put less than that
            .Builder(TaskWorker::class.java, 5, TimeUnit.DAYS)
            .build()
        //--------------------------


        //-------------------------------------
        WorkManager.getInstance(mainActivity).enqueueUniquePeriodicWork(
            "periodicTask",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorker
        )
        //--------------------------------------------

    }

}