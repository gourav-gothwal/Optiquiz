package com.example.quizapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.quizapp.R
import com.example.quizapp.activity.ScoreActivity
import com.example.quizapp.api.ApiClient
import com.example.quizapp.models.Question
import com.example.quizapp.models.QuestionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionPage : Fragment() {

    private lateinit var questionText: TextView
    private lateinit var imageView: ImageView
    private lateinit var optionsContainer: LinearLayout
    private lateinit var questionNumberText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button

    private var currentIndex = 0
    private var questions: List<Question> = listOf()
    private val selectedAnswers = mutableMapOf<Int, String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_question_page, container, false)

        questionText = view.findViewById(R.id.textView15)
        imageView = view.findViewById(R.id.imageView16)
        optionsContainer = view.findViewById(R.id.optionsContainer)
        questionNumberText = view.findViewById(R.id.textView14)
        progressBar = view.findViewById(R.id.progressBar2)
        nextButton = view.findViewById(R.id.button5)
        prevButton = view.findViewById(R.id.button4)

        nextButton.setOnClickListener {
            if (currentIndex < questions.size - 1) {
                currentIndex++
                showQuestion()
            } else {
                // Calculate and pass results
                var correctCount = 0
                for (i in questions.indices) {
                    if (selectedAnswers[i] == questions[i].correct_answer) {
                        correctCount++
                    }
                }

                val totalQuestions = questions.size
                val incorrectCount = totalQuestions - correctCount
                val totalCoins = correctCount * 5
                val score = totalCoins

                val intent = Intent(requireContext(), ScoreActivity::class.java).apply {
                    putExtra("score", score)
                    putExtra("coins", totalCoins)
                    putExtra("correct", correctCount)
                    putExtra("incorrect", incorrectCount)
                    putExtra("totalQuestions", totalQuestions)
                }
                startActivity(intent)
            }
        }

        prevButton.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                showQuestion()
            }
        }

        fetchQuestions("easy")
        return view
    }

    private fun fetchQuestions(difficulty: String) {
        ApiClient.api.getQuestions(
            amount = 10,
            categoryId = 9,
            difficulty = difficulty,
            type = "multiple"
        ).enqueue(object : Callback<QuestionResponse> {
            override fun onResponse(call: Call<QuestionResponse>, response: Response<QuestionResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    questions = response.body()!!.results
                    showQuestion()
                }
            }

            override fun onFailure(call: Call<QuestionResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to load questions", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showQuestion() {
        if (questions.isEmpty()) return

        val question = questions[currentIndex]
        questionNumberText.text = "Question ${currentIndex + 1} of ${questions.size}"
        progressBar.progress = ((currentIndex + 1) * 100) / questions.size
        questionText.text = HtmlCompat.fromHtml(question.question, HtmlCompat.FROM_HTML_MODE_LEGACY)

        if (!question.image.isNullOrEmpty()) {
            imageView.visibility = View.VISIBLE
            Glide.with(this).load(question.image).into(imageView)
        } else {
            imageView.visibility = View.GONE
        }

        val options = (question.incorrect_answers + question.correct_answer).shuffled()
        optionsContainer.removeAllViews()

        options.forEach { option ->
            val btn = Button(requireContext()).apply {
                text = HtmlCompat.fromHtml(option, HtmlCompat.FROM_HTML_MODE_LEGACY)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { topMargin = 12 }

                setOnClickListener {
                    selectedAnswers[currentIndex] = option
                    updateOptionColors(question.correct_answer)
                }
            }
            optionsContainer.addView(btn)
        }

        if (selectedAnswers.containsKey(currentIndex)) {
            updateOptionColors(question.correct_answer)
        }
    }

    private fun updateOptionColors(correctAnswer: String) {
        for (i in 0 until optionsContainer.childCount) {
            val btn = optionsContainer.getChildAt(i) as Button
            val selected = selectedAnswers[currentIndex]
            val isCorrect = btn.text.toString() == correctAnswer
            val isSelected = btn.text.toString() == selected

            when {
                isCorrect -> btn.setBackgroundResource(R.drawable.green_bg)
                isSelected && !isCorrect -> btn.setBackgroundResource(R.drawable.red_bg)
                else -> btn.setBackgroundResource(android.R.drawable.btn_default)
            }
        }
    }
}
