package com.tuwaiq.husam.taskstodoapp.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MockBuilder {
    private const val BASE_URL = "https://61aded20a7c7f3001786f443.mockapi.io/"
    private fun retrofit(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val mockApi: MockApi = retrofit().create(MockApi::class.java)

}
