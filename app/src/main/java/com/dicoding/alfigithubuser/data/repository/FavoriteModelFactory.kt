package com.dicoding.alfigithubuser.data.repository

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.alfigithubuser.viewmodel.DetailViewModel
import com.dicoding.alfigithubuser.viewmodel.FavoriteViewModel

class FavoriteModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FavoriteModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavoriteModelFactory {
            if (INSTANCE == null) {
                synchronized(FavoriteModelFactory::class.java) {
                    INSTANCE = FavoriteModelFactory(application)
                }
            }
            return INSTANCE as FavoriteModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}