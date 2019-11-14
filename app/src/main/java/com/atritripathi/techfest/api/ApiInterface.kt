package com.atritripathi.techfest.api

import com.atritripathi.techfest.models.Event
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiInterface {

    fun getEvents() : Call<List<Event>>

    companion object {

        var BASE_URL = "https://raw.githubusercontent.com/AtriTripathi/TechFest/master/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}