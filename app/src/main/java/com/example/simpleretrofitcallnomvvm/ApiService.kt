package com.example.simpleretrofitcallnomvvm

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET(value = "photos")
    suspend fun getPhotos(): List<Photo>

    companion object {
        var apiService: ApiService ?= null

        fun getInstance() : ApiService {
            if (apiService == null) {
                apiService = Retrofit
                    .Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }

            return apiService!!
        }
    }
}