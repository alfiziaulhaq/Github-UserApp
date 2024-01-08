package com.dicoding.alfigithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.alfigithubuser.data.response.ItemsItem
import com.dicoding.alfigithubuser.data.response.UserResponse
import com.dicoding.alfigithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentViewModel: ViewModel() {
    private val _listfollower= MutableLiveData<List<ItemsItem>>()
    val listfollower: LiveData<List<ItemsItem>> = _listfollower
    private val _listfollowing= MutableLiveData<List<ItemsItem>>()
    val listfollowing: LiveData<List<ItemsItem>> = _listfollowing
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
        private const val parameter_query = "alfizi"
    }

    fun findFollower(user:String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollower(user)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listfollower.postValue(response.body())
                } else {
                    _isLoading.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false

                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
    fun findFollowing(user:String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(user)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listfollowing.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


}