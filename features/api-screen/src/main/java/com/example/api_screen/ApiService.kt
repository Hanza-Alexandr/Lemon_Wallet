package com.example.api_screen

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}

val asdt = OkHttpClient().newBuilder().build()


val request = Request.Builder()

