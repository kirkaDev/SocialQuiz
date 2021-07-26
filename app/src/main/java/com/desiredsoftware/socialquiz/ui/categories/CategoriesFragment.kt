package com.desiredsoftware.socialquiz.ui.categories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.di.App
import com.desiredsoftware.socialquiz.model.category.Category
import com.desiredsoftware.socialquiz.presenter.CategoriesPresenter
import com.desiredsoftware.socialquiz.ui.components.CategoriesAdapter
import com.desiredsoftware.socialquiz.ui.components.OnClickCategoryListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class CategoriesFragment : MvpAppCompatFragment(), CategoriesPresenter.ICategoriesView {

    @Inject
    lateinit var presenterProvider: Provider<CategoriesPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    lateinit var categoryList: RecyclerView

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
        categoryList = root.findViewById(R.id.recyclerViewCategory)

        var navController = requireParentFragment().findNavController()

        return root
    }

    override fun showCategories(categoriesList: List<Category>) {
        val listener = object : OnClickCategoryListener {
            override fun onClicked(categoryName: String) {
                fun onClicked(categoryName: String) {}
            }
        }

        categoryList.apply {
            adapter = CategoriesAdapter(categoriesList as ArrayList<Category>, listener)
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    override fun openCategory(idCategory: String) {
        TODO("Not yet implemented")
    }


}