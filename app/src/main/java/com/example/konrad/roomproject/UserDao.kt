package com.example.konrad.roomproject

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

/**
 * Created by Konrad on 15.04.2018.
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAllUsers() : LiveData<List<User>>

    @Insert(onConflict = REPLACE)
    fun insertAll(vararg user : User)

    @Query("DELETE from User")
    fun deleteAll()

    @Delete
    fun deleteUser(user:User)
}