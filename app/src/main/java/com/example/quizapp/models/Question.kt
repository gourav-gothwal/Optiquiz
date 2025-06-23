package com.example.quizapp.models

import com.google.gson.annotations.SerializedName

data class Question(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
) {
    var image: String? = null
}
