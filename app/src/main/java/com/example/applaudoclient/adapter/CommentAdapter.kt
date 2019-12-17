package com.example.applaudoclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applaudoclient.R
import com.example.applaudoclient.model.Comment


class CommentAdapter(private val listPost: List<Comment>) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPost.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = listPost[position].name
        holder.tvBody.text = listPost[position].body
        holder.tvUserId.text = listPost[position].id.toString()
        holder.tvEmail.text = listPost[position].email
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserId: TextView = itemView.findViewById(R.id.comment_id_user)
        val tvBody: TextView = itemView.findViewById(R.id.comment_post)
        val tvName: TextView = itemView.findViewById(R.id.comment_name)
        val tvEmail: TextView = itemView.findViewById(R.id.comment_email_user)
    }


}