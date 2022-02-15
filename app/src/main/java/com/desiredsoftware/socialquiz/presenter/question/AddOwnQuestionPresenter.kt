package com.desiredsoftware.socialquiz.presenter.question

import android.content.Context
import com.desiredsoftware.socialquiz.data.model.category.Category
import com.desiredsoftware.socialquiz.data.model.question.Answer
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

    val answersCount = 4

    var proposedQuestion: Question = Question()
    var answers: MutableList<Answer> = MutableList(answersCount) {
        Answer("", false)
    }

    fun initAnswersList(){
        presenterScope.launch {
            viewState.initAnswersList(answers)
        }
    }

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
        fun initAnswersList(answersList: MutableList<Answer>)
    }
}