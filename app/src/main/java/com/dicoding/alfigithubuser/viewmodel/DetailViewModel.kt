package com.dicoding.alfigithubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.alfigithubuser.data.database.Favorite
import com.dicoding.alfigithubuser.data.repository.FavoriteRepository
import com.dicoding.alfigithubuser.data.response.DetailResponse
import com.dicoding.alfigithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application : Application): ViewModel() {

    private val dataDetail = MutableLiveData<DetailResponse>()
    val datadetail: LiveData<DetailResponse> = dataDetail
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val mNoteRepository: FavoriteRepository = FavoriteRepository(application)

    val getAll: LiveData<List<Favorite>> = mNoteRepository.getAll()
    fun getUser(username: String): LiveData<Favorite> =
        mNoteRepository.getUser(username)
    fun insert(note: Favorite) {
        mNoteRepository.insert(note)
    }
    fun update(note: Favorite) {
        mNoteRepository.update(note)
    }
    fun delete(note: Favorite) {
        mNoteRepository.delete(note)
    }

    companion object{
        private const val TAG = "DetailViewModel"
        const val EXTRA_USERNAME="extra_username"
    }

    fun findDetail(data:String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetail(data)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    dataDetail.value = response.body()

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}