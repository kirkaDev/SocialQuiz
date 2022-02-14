package com.desiredsoftware.socialquiz.ui.question

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.desiredsoftware.socialquiz.data.model.category.Category
import com.desiredsoftware.socialquiz.databinding.ViewControllerAddOwnQuestionBinding
import com.desiredsoftware.socialquiz.di.App
import com.desiredsoftware.socialquiz.presenter.question.AddOwnQuestionPresenter
import com.desiredsoftware.socialquiz.ui.common.MvpController
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class AddOwnQuestionController : MvpController(), AddOwnQuestionPresenter.IAddOwnQuestionView {

    private var _binding: ViewControllerAddOwnQuestionBinding? = null
    private val binding get() = _binding!!

    @Inject
    @InjectPresenter
    lateinit var presenter: AddOwnQuestionPresenter

    @ProvidePresenter
    fun provides() = presenter

    override fun inject() {
        super.inject()
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        _binding = ViewControllerAddOwnQuestionBinding.inflate(inflater, container, false)

        presenter.initCategoriesSpinner()


        return binding.root
    }

    override fun initCategoriesSpinner(categoriesList: List<Category>) {
        val spinnerAdapter = ArrayAdapter(
                activity?.applicationContext!!,
                R.layout.simple_spinner_item,
                categoriesList.map { category ->
                    category.categoryName
                }
            )

        binding.categoriesSpinner.adapter = spinnerAdapter
        binding.categoriesSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.d("CREATE_QUESTION", "category with id=${categoriesList[position].id} selected")
                    presenter.proposedQuestion.categoryId = categoriesList[position].id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}