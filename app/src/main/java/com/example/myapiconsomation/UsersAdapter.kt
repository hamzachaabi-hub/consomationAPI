package com.example.myapiconsomation



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


// on below line we are creating a course rv adapter class.
class UsersAdapter(
    // on below line we are passing variables as course list and context
    private var userList: ArrayList<UsersRvModel>,

) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsersAdapter.UserViewHolder {
        // this method is use to inflate the layout file
        // which we have created for our recycler view.
        // on below line we are inflating our layout file.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.user_item,
            parent, false
        )
        // at last we are returning our view
        // holder class with our item View File.
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UsersAdapter.UserViewHolder, position: Int) {
        // on below line we are setting data to our text view and our image view.
        holder.userid.text = userList.get(position).userid
        holder.userName.text = userList.get(position).userName
        holder.useremail.text = userList.get(position).useremail

    }

    override fun getItemCount(): Int {
        // on below line we are returning
        // our size of our list
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // on below line we are initializing our course name
        // text view and our image view.
        val userid: TextView = itemView.findViewById(R.id.iduser)
        val userName: TextView = itemView.findViewById(R.id.idname)
        val useremail: TextView = itemView.findViewById(R.id.idemail)

    }
}

