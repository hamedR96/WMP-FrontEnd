package com.faircorp.model

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiServices {
    val roomsApiService: RoomApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://app-93822805-07ad-40fe-b4ee-b61393b1b542.cleverapps.io/api/")
            .build()
            .create(RoomApiService::class.java)
    }

    val windowsApiService : WindowApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://app-93822805-07ad-40fe-b4ee-b61393b1b542.cleverapps.io/api/")
            .build()
            .create(WindowApiService::class.java)
    }

}