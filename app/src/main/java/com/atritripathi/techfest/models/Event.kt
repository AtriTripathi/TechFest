package com.atritripathi.techfest.models

import android.os.Parcel
import android.os.Parcelable


data class Event(

    val id: String? = null,

    val title: String? = null,

    val time: String? = null,

    val body: String? = null,

    val techiePoints: String? = null

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as Event

        if (title != other.title) return false

        return true
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(time)
        parcel.writeString(body)
        parcel.writeString(techiePoints)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (time?.hashCode() ?: 0)
        result = 31 * result + (body?.hashCode() ?: 0)
        result = 31 * result + (techiePoints?.hashCode() ?: 0)
        return result
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}