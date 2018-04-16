package com.example.konrad.roomproject

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Konrad on 15.04.2018.
 */
@Entity(tableName = "User")
class User(
        @PrimaryKey(autoGenerate = true)
        var id : Int?,
        @ColumnInfo(name = "login")
        var login: String,
        @ColumnInfo(name = "password")
        var password: String
){
        constructor():this(null,"","")
}