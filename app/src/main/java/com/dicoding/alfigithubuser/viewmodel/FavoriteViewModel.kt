package com.dicoding.alfigithubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.alfigithubuser.data.database.Favorite
import com.dicoding.alfigithubuser.data.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteUsersRepository: FavoriteRepository =
        FavoriteRepository(application)

    fun getAll(): LiveData<List<Favorite>> =
        mFavoriteUsersRepository.getAll()
}