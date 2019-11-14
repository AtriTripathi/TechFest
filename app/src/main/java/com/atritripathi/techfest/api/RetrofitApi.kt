package com.atritripathi.techfest.api

import android.telecom.Call
import jdk.nashorn.internal.runtime.PropertyDescriptor.GET


interface RetrofitAPI {

    @get:GET("resources/team.json")
    val team: Call<TeamResponse>

    @get:GET("resources/events.json")
    val events: Call<EventsResponse>

    @get:GET("resources/updates.json")
    val updates: Call<ArrayList<UpdatesResponse>>

    companion object {



        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}