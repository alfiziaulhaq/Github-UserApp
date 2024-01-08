package com.dicoding.alfigithubuser.data.retrofit

import com.dicoding.alfigithubuser.data.response.DetailResponse
import com.dicoding.alfigithubuser.data.response.ItemsItem
import com.dicoding.alfigithubuser.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // If token expired. you can generate your own new github token. see on https://www.dicoding.com/blog/apa-itu-rate-limit-pada-github-api/
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetail(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollower(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

}