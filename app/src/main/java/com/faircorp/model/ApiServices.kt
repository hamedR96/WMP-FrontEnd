package com.faircorp.model

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiServices {
    val roomsApiService: RoomApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://app-18ab8dca-235a-47eb-b6be-da6545401df1.cleverapps.io/api/")
            .build()
            .create(RoomApiService::class.java)
    }

    val windowsApiService : WindowApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://app-18ab8dca-235a-47eb-b6be-da6545401df1.cleverapps.io/api/")
            .build()
            .create(WindowApiService::class.java)
    }

}
