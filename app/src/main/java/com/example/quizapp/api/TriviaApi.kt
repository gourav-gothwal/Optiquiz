package com.example.quizapp.api

import com.example.quizapp.models.CategoryResponse
import com.example.quizapp.models.QuestionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaApi {

    @GET("api_category.php")
    fun getCategories(): Call<CategoryResponse>

    @GET("api.php")
    fun getQuestions(
        @Query("amount") amount: Int,
        @Query("category") categoryId: Int,
        @Query("difficulty") difficulty: String?,
        @Query("type") type: String?
    ): Call<QuestionResponse>
}
