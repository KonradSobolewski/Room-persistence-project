package com.example.konrad.roomproject

import android.app.Activity
import android.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.user_row.view.*
import android.view.animation.AnimationUtils


class UserAdapter(
        private var users : List<User>,
        private val activity: Activity,
        private var usersViewModel: UsersViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.user_row, parent, false)
        return UserHolder(view)
    }

    fun changeItems(userList: List<User>) {
        users = userList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val viewHolder = holder as UserHolder
        viewHolder.run {
            setLogin("Login: ${users[position].login}")
            setPass("Pass: ${users[position].password}")

            itemView.setOnLongClickListener({
                AlertDialog.Builder(activity).run {
                    setTitle("Deleting user")
                    setMessage("Are you sure you want to delete user?")
                    setPositiveButton("Do it!", { _, _ ->
                        usersViewModel.deleteUser(users[position])
                        lastPosition -= 1
                    })
                    setNegativeButton("Cancel", { dialog, _ -> dialog.dismiss() })
                    show()
                }
                true
            })
        }
        setAnimation(viewHolder.itemView, position)
    }


    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(activity.applicationContext, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    class UserHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            fun setLogin(name : String) {
                val login  = view.loginCard
                login.text = name
            }
            fun setPass(name : String) {
                val pass  = view.passwordCard
                pass.text = name
            }
    }
}
