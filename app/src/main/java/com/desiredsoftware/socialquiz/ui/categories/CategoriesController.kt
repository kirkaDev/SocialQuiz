package com.desiredsoftware.socialquiz.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluelinelabs.conductor.Controller
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.di.App
import com.desiredsoftware.socialquiz.model.category.Category
import com.desiredsoftware.socialquiz.presenter.CategoriesPresenter
import com.desiredsoftware.socialquiz.ui.components.CategoriesAdapter
import com.desiredsoftware.socialquiz.ui.components.OnClickCategoryListener
import javax.inject.Inject

class CategoriesController : Controller(), CategoriesPresenter.ICategoriesView {

    @Inject
    lateinit var presenter: CategoriesPresenter

    lateinit var categoryList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.controller_select_category, container, false)
        categoryList = view.findViewById(R.id.recyclerViewCategory)

        App.appComponent.inject(this)
        presenter.attachView(this)
        presenter.showCategories()

        return view
    }

    override fun showCategories(categoriesList: List<Category>) {
        val listener = object : OnClickCategoryListener {
            override fun onClicked(categoryId: String) {
                Toast.makeText(
                    activity,
                    "Category with id ${categoryId} clicked",
                    Toast.LENGTH_SHORT
                ).show()
                presenter.openCategory(categoryId)
            }
        }

        categoryList.apply {
            adapter = CategoriesAdapter(categoriesList as ArrayList<Category>, listener)
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    override fun openCategory(idCategory: String) {

    }
}