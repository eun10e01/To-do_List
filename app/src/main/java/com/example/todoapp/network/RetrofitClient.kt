package com.example.todoapp.network

import com.example.todoapp.api.TodoApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    // 실제 안드로이드 폰으로 테스트 할 경우 실제 내 PC의 IP주소 사용해야 함
    //private const val BASE_URL = "http://192.255.255.255:8080/"
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val todoApiService: TodoApi by lazy {
        retrofit.create(TodoApi::class.java)
    }
}