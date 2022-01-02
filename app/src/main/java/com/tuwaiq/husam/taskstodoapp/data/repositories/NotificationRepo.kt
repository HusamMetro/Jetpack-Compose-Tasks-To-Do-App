package com.tuwaiq.husam.taskstodoapp.data.repositories

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.work.*
import com.tuwaiq.husam.taskstodoapp.MainActivity
import com.tuwaiq.husam.taskstodoapp.data.notifcation.TaskWorker
import java.util.concurrent.TimeUnit

class NotificationRepo {
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    fun displayNotification(mainActivity: MainActivity) {
        /*val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()*/

        /*  val oneTimeWorker = OneTimeWorkRequest
              .Builder(TaskWorker::class.java)
              .setConstraints(constraints)
              .build()*/

       // One time Worker for android 12
        /*val request = OneTimeWorkRequestBuilder<TaskWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()
        WorkManager.getInstance(mainActivity).enqueue(request)*/


        //------------------------------
        val periodicWorker = PeriodicWorkRequest
            // minimum time 15 Minutes even if i put less than that
            .Builder(TaskWorker::class.java, 15, TimeUnit.SECONDS)
            .build()
        //--------------------------


//        WorkManager.getInstance(mainActivity).enqueue(oneTimeWorker)
        /*WorkManager.getInstance(mainActivity).enqueue(oneTimeWorker)
        WorkManager.getInstance(mainActivity).getWorkInfoByIdLiveData(oneTimeWorker.id)
            .observe(mainActivity) {
                if (it.state == WorkInfo.State.SUCCEEDED) {
                    stockPrice.postValue(it.outputData.getString("lastStockValue"))
                }
            }*/
//        WorkManager.getInstance().cancelAllWork()


        //-------------------------------------
        WorkManager.getInstance(mainActivity).enqueueUniquePeriodicWork(
            "periodicTask",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorker
        )
        //--------------------------------------------



//        WorkManager.getInstance().cancelUniqueWork("periodicStockWorker")
    }

}