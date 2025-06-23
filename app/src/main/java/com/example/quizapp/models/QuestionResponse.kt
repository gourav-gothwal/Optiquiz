package com.example.quizapp.models

import com.google.gson.annotations.SerializedName

data class QuestionResponse(
    @SerializedName("response_code")
    val responseCode: Int,

    val results: List<Question>
)
