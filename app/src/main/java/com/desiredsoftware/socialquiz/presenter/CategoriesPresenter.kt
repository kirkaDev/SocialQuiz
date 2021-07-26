package com.desiredsoftware.socialquiz.presenter

import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import javax.inject.Inject

@InjectViewState
class CategoriesPresenter @Inject constructor(
    private var firebaseRepository : FirebaseRepository
) : MvpPresenter<CategoriesPresenter.ICategoriesView>() {

    fun showCategories(){
        viewState.showCategories()
    }

    fun openCategory(idCategory: String)
    {
        viewState.openCategory()
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface ICategoriesView : MvpView{
        fun showCategories()
        fun openCategory()
    }
}