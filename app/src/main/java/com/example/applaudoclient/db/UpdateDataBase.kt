package com.example.applaudoclient.db

import android.content.Context
import androidx.work.*
import com.example.applaudoclient.api.Api
import com.example.applaudoclient.model.Post
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.TimeUnit

class UpdateDataBase(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    private val db = DatabaseHandler(appContext)

    override fun doWork(): Result {
        val call: Call<List<Post>> = Api.retrofit().getPosts()
        val result:Response<List<Post>> = call.execute()
        if (result.isSuccessful) {
            db.deleteAll()
            result.body()?.forEach { post ->
                db.updatePost(Post(post.id, post.userId, post.title, post.body))
            }
            return Result.success()
        }
        return Result.failure()
    }

    companion object {

        fun start(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val saveRequest =
                PeriodicWorkRequestBuilder<UpdateDataBase>(15L, TimeUnit.MINUTES)
                    .setInitialDelay(1L, TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .build()
            WorkManager.getInstance(context)
                .enqueue(saveRequest)

        }

    }

}