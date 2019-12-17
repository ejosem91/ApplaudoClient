package com.example.applaudoclient.api


import com.example.applaudoclient.model.Comment
import com.example.applaudoclient.model.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @GET("comments?")
    fun getComment(@Query("postId") id: Int?): Call<List<Comment>>

}