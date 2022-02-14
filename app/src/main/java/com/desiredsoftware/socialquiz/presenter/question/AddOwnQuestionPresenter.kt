package com.desiredsoftware.socialquiz.presenter.question

import android.content.Context
import com.desiredsoftware.socialquiz.data.model.category.Category
import com.desiredsoftware.socialquiz.data.model.question.Question
import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
import com.desiredsoftware.socialquiz.view.IError
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenterScope
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import javax.inject.Inject

@InjectViewState
class AddOwnQuestionPresenter @Inject constructor(
    private var firebaseRepository: FirebaseRepository,
    private var context: Context
) : MvpPresenter<AddOwnQuestionPresenter.IAddOwnQuestionView>() {

    var proposedQuestion: Question = Question()

    fun initCategoriesSpinner(){
        presenterScope.launch {
            viewState.initCategoriesSpinner(
                firebaseRepository.getCategories()
            )
        }
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface IAddOwnQuestionView : MvpView, IError {
        fun initCategoriesSpinner(categoriesList: List<Category>)
    }
}