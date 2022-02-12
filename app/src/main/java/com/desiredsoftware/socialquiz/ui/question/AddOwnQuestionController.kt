package com.desiredsoftware.socialquiz.ui.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.desiredsoftware.socialquiz.data.model.category.Category
import com.desiredsoftware.socialquiz.databinding.ViewControllerAddOwnQuestionBinding
import com.desiredsoftware.socialquiz.di.App
import com.desiredsoftware.socialquiz.presenter.question.AddOwnQuestionPresenter
import com.desiredsoftware.socialquiz.ui.common.MvpController
import com.desiredsoftware.socialquiz.ui.question.adapters.CustomSpinnerAdapter
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

        binding.categoriesSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        return binding.root
    }

    override fun initCategoriesSpinner(categoriesList: List<Category>) {
        val spinnerAdapter = activity?.applicationContext?.let { CustomSpinnerAdapter(it, categoriesList, ::onItemChecked) }
        binding.categoriesSpinner.adapter = spinnerAdapter
    }

    fun onItemChecked(category: Category){

    }

    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}