package com.desiredsoftware.socialquiz.presenter.categories

import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
import com.desiredsoftware.socialquiz.model.category.Category
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenterScope
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import javax.inject.Inject

@InjectViewState
class CategoriesPresenter @Inject constructor(
        private var firebaseRepository: FirebaseRepository
) : MvpPresenter<CategoriesPresenter.ICategoriesView>() {

    fun showCategories() {
        presenterScope.launch {
            val categoriesList = firebaseRepository.getCategories()
            viewState.showCategories(categoriesList)
        }
    }

    fun openCategory(idCategory: String) {
        viewState.openCategory(idCategory)
    }

    @StateStrategyType(OneExecutionStateStrategy::class)
    interface ICategoriesView : MvpView {
        fun showCategories(categoriesList: List<Category>)
        fun openCategory(idCategory: String)
    }
}