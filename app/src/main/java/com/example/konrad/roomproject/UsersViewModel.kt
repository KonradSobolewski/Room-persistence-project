package com.example.konrad.roomproject


import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask

/**
 * Created by Konrad on 16.04.2018.
 */
class UsersViewModel(application: Application) : AndroidViewModel(application) {

    var mutableLiveData: LiveData<List<User>> = MutableLiveData()
    private val myDb : AppDatabase by lazy {
        AppDatabase.getInstance(application)!!
    }

    fun deleteUser(user: User){
        DeleteAsyncTask().execute(user)
    }

    fun getItems() : LiveData<List<User>> {
        mutableLiveData = myDb.userDao().getAllUsers()
        return mutableLiveData
    }

    fun deleteAll(){
        DeleteAllAsyncTask().execute()
    }

    fun insertUsers( user: User ){
        InsertAsyncTask().execute(user)
    }

    @SuppressLint("StaticFieldLeak")
    inner class InsertAsyncTask : AsyncTask<User, Void, Boolean>() {
        override fun doInBackground(vararg user: User): Boolean {
            myDb.userDao().insertAll(user[0])
            return true
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class DeleteAsyncTask : AsyncTask<User, Void, Boolean>() {
        override fun doInBackground(vararg user: User?): Boolean {
            myDb.userDao().deleteUser(user[0]!!)
            return true
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class DeleteAllAsyncTask: AsyncTask<Void, Void, Boolean>(){
        override fun doInBackground(vararg p0: Void?): Boolean {
            myDb.userDao().deleteAll()
            return true
        }
    }
}