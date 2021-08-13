package com.desiredsoftware.socialquiz.presenter.question

import android.content.Context
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import javax.inject.Inject

@InjectViewState
class QuestionResultPresenter @Inject constructor(
    private var firebaseRepository: FirebaseRepository,
    private var context: Context
) : MvpPresenter<QuestionResultPresenter.IQuestionResultView>() {

    fun showResult(answerIsCorrect: Boolean){
        val result: String = context.getString(R.string.answerIncorrect)

        if (answerIsCorrect) viewState.showResult(context.getString(R.string.answerCorrect))
        else viewState.showResult(context.getString(R.string.answerIncorrect))
    }

    fun tryAgain(){
        viewState.tryAgain()
    }

    fun nextQuestion(){
        viewState.nextQuestion()
    }

    fun selectCategory(){
        viewState.selectCategory()
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface IQuestionResultView : MvpView {
        fun showResult(result: String)
        fun tryAgain()
        fun nextQuestion()
        fun selectCategory()
    }
}