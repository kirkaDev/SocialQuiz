package com.desiredsoftware.socialquiz.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.api.`in`.ApiClientFirebase
import com.desiredsoftware.socialquiz.api.`in`.category.GetCategoriesCallback
import com.desiredsoftware.socialquiz.data.model.question.GetQuestionsCallback
import com.desiredsoftware.socialquiz.data.model.question.QuestionCategory
import com.desiredsoftware.socialquiz.ui.components.CategoriesAdapter
import com.desiredsoftware.socialquiz.ui.components.OnClickCategoryListener
import com.desiredsoftware.socialquiz.utils.convertToQuestion
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    lateinit var navController: NavController

    val apiClient: ApiClientFirebase = ApiClientFirebase()

    lateinit var categoriesList: ArrayList<QuestionCategory>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.textViewSelectCategory)
        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerViewCategory)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        categoriesList = ArrayList()

        navController = requireParentFragment().findNavController()

        apiClient.getCategories(object : GetCategoriesCallback {
            override fun onCallback(categories: ArrayList<QuestionCategory>) {
                categoriesList = categories

                recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

                recyclerView.adapter = CategoriesAdapter(
                        categoriesList,
                        object : OnClickCategoryListener {
                            override fun onClicked(categoryName: String) {
                                Log.d("RecyclerView clicked", "Category name = $categoryName was selected")
                                homeViewModel.getQuestionsInCategory(categoryName, object : GetQuestionsCallback {
                                    override fun onCallback(questions: ArrayList<Any>) {
                                        if (questions != null) {
                                            // Select random question

                                            val randomNumber = Random().nextInt(questions.size)
                                            val currentQuestion = convertToQuestion(questions[randomNumber] as HashMap<String, Any>)
                                            val action = HomeFragmentDirections.actionNavigationHomeToQuestionShowingFragment(currentQuestion)
                                            navController.navigate(action)
                                        }
                                    }
                                })
                            }
                        })
            }
        })

        return root
    }

}