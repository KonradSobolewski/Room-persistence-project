package com.example.konrad.roomproject

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.konrad.roomproject.R.id.addItem
import com.example.konrad.roomproject.R.id.deleteAll
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.create_user.view.*

class MainActivity : AppCompatActivity()  {

    private val mAdapter : UserAdapter by lazy {
        UserAdapter(usersViewModel.mutableLiveData.value ?: emptyList(),
                this, usersViewModel)
    }
    private val usersViewModel : UsersViewModel by lazy {
        ViewModelProviders.of(this).get(UsersViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersViewModel.getItems().observe(this, Observer{
            mAdapter.changeItems(it ?: emptyList())
        })

        recycleView.apply {
            adapter = mAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
            addItemDecoration(DividerItemDecoration(applicationContext,DividerItemDecoration.VERTICAL))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId) {
            deleteAll -> deleteUsers()
            addItem -> addAlert()
            else -> false
    }

    private fun deleteUsers():Boolean {
        mAdapter.lastPosition = -1
        usersViewModel.deleteAll()
        Toast.makeText(this,"Database cleared", Toast.LENGTH_SHORT).show()
        return true
    }

    private fun addAlert(): Boolean {
        val view : View = layoutInflater.inflate(R.layout.create_user,null)
        val dialog  = AlertDialog.Builder(this)
        dialog.setView(view)
        val alertDialog = dialog.create()
        view.cancelBtn.setOnClickListener({
                alertDialog.dismiss()
        })
        view.acceptBtn.setOnClickListener({
            if( TextUtils.isEmpty(view.passwordText.editableText) || TextUtils.isEmpty(view.loginText.editableText) ){
                Toast.makeText(this,"Complete fields", Toast.LENGTH_SHORT).show()
            }
            else{
                val user = User(null,view.loginText.text.toString(),
                        view.passwordText.text.toString())
                usersViewModel.insertUsers(user)
                alertDialog.dismiss()
            }
        })
        alertDialog.show()
        return true
    }
}
