package com.example.konrad.roomproject

import android.arch.persistence.room.*
import android.content.Context

/**
 * Created by Konrad on 15.04.2018.
 */

@Database(entities = [(User::class)], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao

    companion object {
        private var INSTANCE : AppDatabase?= null

        fun getInstance(context: Context) : AppDatabase?{
            if ( INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java,"database")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}