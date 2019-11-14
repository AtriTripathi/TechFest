package com.atritripathi.techfest.api

import jdk.nashorn.internal.runtime.PropertyDescriptor.GET
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface RetrofitAPI {

    @get:GET("resources/team.json")
    val team: Call

    @get:GET("resources/events.json")
    val events: Call

    @get:GET("resources/updates.json")
    val updates: Call<ArrayList>

    companion object {

        const val BASE_URL = "https://raw.githubusercontent.com/

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}