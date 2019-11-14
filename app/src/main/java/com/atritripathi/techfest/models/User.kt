package com.atritripathi.techfest.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    var pk: Int,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "username")
    var username: String
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as User

        if (pk != other.pk) return false
        if (email != other.email) return false
        if (username != other.username) return false

        return true
    }

}