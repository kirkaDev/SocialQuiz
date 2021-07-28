package com.desiredsoftware.socialquiz.presenter.question

import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import javax.inject.Inject

@InjectViewState
class QuestionShowingPresenter @Inject constructor(
    private var firebaseRepository: FirebaseRepository
) : MvpPresenter<QuestionShowingPresenter.IQuestionView>() {

/*    fun showCategories() {
        presenterScope.launch {
            val categoriesList = firebaseRepository.getCategories()
            viewState.showCategories(categoriesList)
        }
    }*/

    fun getCurrentQuestion()
    {

    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface IQuestionView : MvpView {
        fun showQuestionBody()
        fun showAnswerVariants()
    }
}