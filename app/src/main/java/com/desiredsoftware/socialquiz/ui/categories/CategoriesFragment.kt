package com.desiredsoftware.socialquiz.ui.categories

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.di.App
import com.desiredsoftware.socialquiz.presenter.CategoriesPresenter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class CategoriesFragment : MvpAppCompatFragment(), CategoriesPresenter.ICategoriesView {

    @Inject
    lateinit var presenterProvider: Provider<CategoriesPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    lateinit var navController: NavController

    override fun onAttach(context: Context) {
        App.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreate(savedInstanceState)
        presenter?.showCategories()

        val root = inflater.inflate(R.layout.fragment_select_category, container, false)
        val textView: TextView = root.findViewById(R.id.textViewSelectCategory)
        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerViewCategory)

        var navController = requireParentFragment().findNavController()

/*        apiClient.getCategories(object : GetCategoriesCallback {
            override fun onCallback(categories: ArrayList<QuestionCategory>) {
                categoriesList = categories

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
                                        }
                                    }
                                })
                            }
                        })
            }
        })*/

        return root
    }

    override fun showCategories() {
        Log.d("Dagger", "showCategories method called from Fragment, presenter is good!")
        //TODO("Not yet implemented")
    }

    override fun openCategory() {
        //TODO("Not yet implemented")
    }

}