package com.tuwaiq.husam.taskstodoapp.data.repositories

import androidx.lifecycle.LiveData
import com.tuwaiq.husam.taskstodoapp.data.models.MockToDoTask
import com.tuwaiq.husam.taskstodoapp.data.models.ToDoTask
import com.tuwaiq.husam.taskstodoapp.data.network.MockBuilder
import kotlinx.coroutines.flow.Flow

class MockRepo {
    private val api = MockBuilder.mockApi

//    val getMockTasks: Flow<List<MockToDoTask>> = api.fetchTasks()

   suspend fun fetchTasks(): List<MockToDoTask> {
        return api.fetchTasks()
    }
}
/*
private val api = FlickrBuilder.flickrAPI

    suspend fun fetchInterestingList(): FlickrData = withContext(Dispatchers.IO) {
        api.fetchPhotos()
    }

    suspend fun searchPhotos(searchKeyword: String): FlickrData = withContext(Dispatchers.IO) {
        api.searchPhotos(searchKeyword)
    }

*/