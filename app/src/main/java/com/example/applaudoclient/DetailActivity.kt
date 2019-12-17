package com.example.applaudoclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applaudoclient.adapter.CommentAdapter
import com.example.applaudoclient.api.Api
import com.example.applaudoclient.model.Comment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {


    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activitydetail_post)
        recyclerView = findViewById(R.id.rv_comment)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val bundle = intent.extras
        val id: Int? = bundle?.getInt("id")
        getDetail(id)
    }


    private fun getDetail(id: Int?) {
        val call: Call<List<Comment>> = Api.retrofit().getComment(id)
        call.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>?, response: Response<List<Comment>>) {
                val commentList = response.body()
                recyclerView.adapter = CommentAdapter(commentList)
            }

            override fun onFailure(call: Call<List<Comment>>?, t: Throwable?) {
            }
        })

    }
}
