package com.example.quizapp.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.adapters.CategoryAdapter
import com.example.quizapp.api.ApiClient
import com.example.quizapp.api.TriviaApi
import com.example.quizapp.models.Category
import com.example.quizapp.models.CategoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePage : Fragment() {

    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_categories)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        fetchCategories()

        return view
    }

    private fun fetchCategories() {
        val triviaApi = ApiClient.api
        val call = triviaApi.getCategories()

        call.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if (response.isSuccessful) {
                    val categories = response.body()?.trivia_categories ?: emptyList()
                    setupRecyclerView(categories)
                } else {
                    Toast.makeText(requireContext(), "Failed to load categories", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "API Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView(categories: List<Category>) {
        val adapter = CategoryAdapter(requireContext(), categories, object : CategoryAdapter.OnCategoryClickListener {
            override fun onCategoryClick(category: Category) {
                showDifficultyDialog(category.id, category.name)
            }
        })
        recyclerView?.adapter = adapter
    }

    private fun showDifficultyDialog(categoryId: Int, categoryName: String) {
        val difficulties = arrayOf("easy", "medium", "hard")

        AlertDialog.Builder(requireContext())
            .setTitle("Select Difficulty for $categoryName")
            .setItems(difficulties) { _, which ->
                val selectedDifficulty = difficulties[which].lowercase()

                val fragment = QuestionPage().apply {
                    arguments = Bundle().apply {
                        putInt("CATEGORY_ID", categoryId)
                        putString("DIFFICULTY", selectedDifficulty)
                    }
                }

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment) 
                    .addToBackStack(null)
                    .commit()
            }
            .show()
    }

}
