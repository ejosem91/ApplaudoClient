package com.example.applaudoclient.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.applaudoclient.model.Post

class DatabaseHandler(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "PostDatabase"
        const val TABLE_POST = "PostTable"
        const val USER_ID = "userId"
        const val KEY_ID = "id"
        const val KEY_TITLE = "title"
        const val KEY_BODY = "body"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE " + TABLE_POST + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + USER_ID + " INTEGER," + KEY_TITLE + " TEXT,"
                + KEY_BODY + " TEXT" + ")"
                )
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS    $TABLE_POST")
        onCreate(db)
    }

    fun savePost(post: Post): Long {
        val db = this.writableDatabase
        val contentVlues = ContentValues()
        contentVlues.put(KEY_ID, post.id)
        contentVlues.put(USER_ID, post.userId)
        contentVlues.put(KEY_BODY, post.body)
        contentVlues.put(KEY_TITLE, post.title)

        val success = db.insert(TABLE_POST, null, contentVlues)
        db.close()

        return success
    }

    fun updatePost(post: Post){
        val db = this.writableDatabase
        val contentVlues = ContentValues()
        contentVlues.put(USER_ID, post.userId)
        contentVlues.put(KEY_BODY, post.body)
        contentVlues.put(KEY_TITLE, post.title)
        db.update(TABLE_POST, contentVlues, "id="+ post.id, null)
        db.close()
    }

    fun deleteAll(): Int {
        val db = this.writableDatabase
        val codeExe = db.delete(TABLE_POST, null, null)
        db.close()
        return codeExe
    }

    fun selectAll(): List<Post> {

        val postList: ArrayList<Post> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_POST"
        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var userId: Int
        var title: String
        var body: String

        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            userId = cursor.getInt(cursor.getColumnIndex(USER_ID))
            title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
            body = cursor.getString(cursor.getColumnIndex(KEY_BODY))

            postList.add(Post(id, userId, title, body))
        }
        cursor.close()
        db.close()
        return postList
    }
}