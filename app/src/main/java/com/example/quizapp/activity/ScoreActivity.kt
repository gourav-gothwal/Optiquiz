package com.example.quizapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.MainActivity
import com.example.quizapp.R

class ScoreActivity : AppCompatActivity() {

    private lateinit var textViewScoreValue: TextView
    private lateinit var textViewTotalCoinsCount: TextView
    private lateinit var textViewCorrectCount: TextView
    private lateinit var textViewIncorrectCount: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewAccuracy: TextView
    private lateinit var buttonReturnHome: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // Bind views
        textViewScoreValue = findViewById(R.id.textViewScoreValue)
        textViewTotalCoinsCount = findViewById(R.id.textViewTotalCoinsCount)
        textViewCorrectCount = findViewById(R.id.textViewCorrectCount)
        textViewIncorrectCount = findViewById(R.id.textViewIncorrectCount)
        progressBar = findViewById(R.id.progressBar)
        textViewAccuracy = findViewById(R.id.textViewAccuracy)
        buttonReturnHome = findViewById(R.id.buttonReturnHome)

        // Get data from intent
        val score = intent.getIntExtra("score", 0)
        val coins = intent.getIntExtra("coins", 0)
        val correct = intent.getIntExtra("correct", 0)
        val incorrect = intent.getIntExtra("incorrect", 0)
        val total = intent.getIntExtra("totalQuestions", 1)

        // Set values to views
        textViewScoreValue.text = score.toString()
        textViewTotalCoinsCount.text = coins.toString()
        textViewCorrectCount.text = "$correct / $total"
        textViewIncorrectCount.text = "$incorrect / $total"

        val accuracy = if (total > 0) (correct * 100) / total else 0
        progressBar.max = 100
        progressBar.progress = accuracy
        textViewAccuracy.text = "$accuracy%"

        buttonReturnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
