package com.desiredsoftware.socialquiz.presenter.question

import android.content.Context
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository.Companion.OPERATION_RESULT_FAILED
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
class QuestionResultPresenter @Inject constructor(
    private var firebaseRepository: FirebaseRepository,
    private var context: Context
) : MvpPresenter<QuestionResultPresenter.IQuestionResultView>() {

    fun showResult(answerIsCorrect: Boolean){
        presenterScope.launch {
            if (answerIsCorrect)
            {
                viewState.showResult(context.getString(R.string.answerCorrect))
                val result = saveResult(POINTS_DEFAULT_VALUE)
                if (result==OPERATION_RESULT_FAILED){
                    viewState.showError(context.resources.getString(R.string.write_result_failed))
                }
            }
            else viewState.showResult(context.getString(R.string.answerIncorrect))
        }
    }

    private suspend fun saveResult(points: Long): Int{
        return firebaseRepository.addPoints(points)
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

    companion object{
        val POINTS_DEFAULT_VALUE = 1L
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface IQuestionResultView : MvpView, IError {
        fun showResult(result: String)
        fun tryAgain()
        fun nextQuestion()
        fun selectCategory()
    }
}