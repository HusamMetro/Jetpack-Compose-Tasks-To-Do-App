package com.tuwaiq.husam.taskstodoapp.data.network

import com.tuwaiq.husam.taskstodoapp.data.models.MockToDoTask
import retrofit2.http.GET

interface MockApi {

    @GET("Task")
    suspend fun fetchTasks(): List<MockToDoTask>

}
