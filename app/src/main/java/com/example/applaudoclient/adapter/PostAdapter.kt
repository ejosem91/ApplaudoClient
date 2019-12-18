package com.example.applaudoclient.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applaudoclient.R
import com.example.applaudoclient.model.Post


class PostAdapter(private val listPost: List<Post>, selectedPost: SelectPost) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private val select = selectedPost

    interface SelectPost {
        fun selectItem(id: Int?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.post_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPost.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = listPost[position].title
        holder.tvBody.text = listPost[position].body

        holder.tvBody.setOnClickListener {
            select.selectItem(listPost[position].id)

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBody: TextView = itemView.findViewById(R.id.body_post)
        val tvTitle: TextView = itemView.findViewById(R.id.title_post)
    }


}