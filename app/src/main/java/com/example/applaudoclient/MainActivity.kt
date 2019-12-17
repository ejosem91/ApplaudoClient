package com.example.applaudoclient.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.AsyncTaskLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applaudoclient.R
import com.example.applaudoclient.adapter.PostAdapter
import com.example.applaudoclient.api.Api
import com.example.applaudoclient.db.DatabaseHandler
import com.example.applaudoclient.model.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<String>,
    PostAdapter.SelectPost {

    var listPost: List<Post>? = null
    private lateinit var dbList: List<Post>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rv_post)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        getData()

    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<String> {
        return object : AsyncTaskLoader<String>(this) {
            override fun loadInBackground(): String? {
                saveData()
                return "SUCCESS"
            }
        }
    }

    override fun onLoadFinished(loader: Loader<String>, data: String?) {
        dbList = getDataFromDb()

        val adapter = PostAdapter(dbList, this)
        recyclerView.adapter = adapter

    }

    override fun onLoaderReset(loader: Loader<String>) {
    }

    private fun getData() {
        val call: Call<List<Post>> = Api.retrofit().getPosts()
        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>?, response: Response<List<Post>>?) {
                listPost = response?.body()
                supportLoaderManager.initLoader(CODE_TASK, null, this@MainActivity).forceLoad()
            }

            override fun onFailure(call: Call<List<Post>>?, t: Throwable?) {
            }
        })
    }

    private fun saveData() {
        val db = DatabaseHandler(this)
        val number = db.deleteAll()
        if (number != -1) {
            listPost?.forEach { post ->
                db.savePost(Post(post.id, post.userId, post.title, post.body))
            }
        }
    }

    private fun getDataFromDb(): List<Post> {
        val db = DatabaseHandler(this)
        return db.selectAll()
    }

    override fun selectItem(id: Int?) {
        val intent = Intent(this, DetailActivity::class.java)
        Toast.makeText(this, "Id Seleccionado $id", Toast.LENGTH_LONG).show()
        intent.putExtra("id", id)
        startActivity(intent)

    }

    companion object {
        const val CODE_TASK = 100
    }

}
