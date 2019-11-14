package com.atritripathi.techfest.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Event(

    @Expose
    @SerializedName("title")
    val title: String? = null,

    @Expose
    @SerializedName("time")
    val time: String? = null,

    @Expose
    @SerializedName("body")
    val body: String? = null
)
{
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as Event

        if (title != other.title) return false

        return true
    }
}