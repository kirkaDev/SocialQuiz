package com.desiredsoftware.socialquiz.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.category.Category
import com.desiredsoftware.socialquiz.di.App
import com.desiredsoftware.socialquiz.presenter.categories.CategoriesPresenter
import com.desiredsoftware.socialquiz.ui.common.MvpController
import com.desiredsoftware.socialquiz.ui.components.CategoriesAdapter
import com.desiredsoftware.socialquiz.ui.components.OnClickCategoryListener
import com.desiredsoftware.socialquiz.ui.question.QuestionShowingController
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class CategoriesController : MvpController(), CategoriesPresenter.ICategoriesView {

    @Inject
    @InjectPresenter
    lateinit var presenter: CategoriesPresenter

    @ProvidePresenter
    fun provides() = presenter

    lateinit var categoryList: RecyclerView

    // Spans number for categories list
    var mSpanCount = SPAN_COUNT_FOR_PHONE

    override fun inject() {
        super.inject()
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        val view: View =
            inflater.inflate(R.layout.view_controller_select_category, container, false)
        categoryList = view.findViewById(R.id.recyclerViewCategory)
        presenter.showCategories()

        return view
    }

    override fun showCategories(categoriesList: List<Category>) {
        val listener = object : OnClickCategoryListener {
            override fun onClicked(categoryId: String) {
                presenter.openCategory(categoryId)
            }
        }

        if(resources?.getBoolean(R.bool.isTablet) == true)
            mSpanCount = SPAN_COUNT_FOR_TABLET

        categoryList.apply {
            adapter = CategoriesAdapter((categoriesList as ArrayList<Category>), listener)
            layoutManager = GridLayoutManager(context, mSpanCount)
        }
    }

    override fun openCategory(idCategory: String) {
        val bundle = Bundle()
        bundle.putString(CATEGORY_ID_KEY, idCategory)
        val transaction = RouterTransaction.with(QuestionShowingController(bundle))
            .pushChangeHandler(VerticalChangeHandler())
            .popChangeHandler(VerticalChangeHandler())
        router.pushController(transaction)
    }

    companion object {
        val CATEGORY_ID_KEY = "CATEGORY_KEY"
        const val SPAN_COUNT_FOR_PHONE = 2
        const val SPAN_COUNT_FOR_TABLET = 4
    }
}