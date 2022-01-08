package com.tuwaiq.husam.taskstodoapp.data.repositories

import com.tuwaiq.husam.taskstodoapp.data.models.MockToDoTask
import com.tuwaiq.husam.taskstodoapp.data.network.MockBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MockRepo {
    private val api = MockBuilder.mockApi

//    val getMockTasks: Flow<List<MockToDoTask>> = api.fetchTasks()

    suspend fun fetchTasks(): List<MockToDoTask> = withContext(Dispatchers.IO) {
        api.fetchTasks()
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