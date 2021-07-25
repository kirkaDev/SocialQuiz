package com.desiredsoftware.socialquiz.presenter

import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import javax.inject.Inject

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

    @AddToEnd
    interface ICategoriesView : MvpView{
        fun showCategories()
        fun openCategory()
    }
}