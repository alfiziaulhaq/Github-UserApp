package com.dicoding.alfigithubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.alfigithubuser.data.database.Favorite
import com.dicoding.alfigithubuser.data.database.FavoriteDAO
import com.dicoding.alfigithubuser.data.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class FavoriteRepository(application: Application) {
    private val mNotesDao: FavoriteDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mNotesDao = db.favoriteDAO()
    }

    fun getUser(username: String): LiveData<Favorite> =
        mNotesDao.getUser(username)

    fun getAll(): LiveData<List<Favorite>> = mNotesDao.getAll()

    fun insert(note: Favorite) {
        executorService.execute { mNotesDao.insert(note) }
    }

    fun delete(note: Favorite) {
        executorService.execute { mNotesDao.delete(note) }
    }

    fun update(note: Favorite) {
        executorService.execute { mNotesDao.update(note) }
    }
}