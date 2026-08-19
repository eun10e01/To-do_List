package com.example.todoapp.api

import com.example.todoapp.dto.TodoCreateRequest
import com.example.todoapp.dto.TodoUpdateRequest
import com.example.todoapp.pages.main.home.ToDoItemData
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoApi {
    @GET("api/todos")
    suspend fun getTodosByDate(
        @Query("userId") userId: Long,
        @Query("date") date: String
    ): List<ToDoItemData>

    @GET("api/todos/completed-dates")
    suspend fun getCompletedDatesByMonth(
        @Query("userId") userId: Long,
        @Query("year") year: Int,
        @Query("month") month: Int
    ): List<String>

    @POST("api/todos")
    suspend fun createTodo(@Body request: TodoCreateRequest): ToDoItemData

    @PATCH("api/todos/{id}/check")
    suspend fun toggleCheck(@Path("id") id: Long)

    @PUT("api/todos/{id}")
    suspend fun updateTodo(@Path("id") id: Long, @Body request: TodoUpdateRequest): ToDoItemData

    @DELETE("api/todos/{id}")
    suspend fun deleteTodo(@Path("id") id: Long)
}