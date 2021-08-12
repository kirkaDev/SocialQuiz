package com.desiredsoftware.socialquiz.presenter.question

import android.content.Context
import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
import com.desiredsoftware.socialquiz.model.question.Answer
import com.desiredsoftware.socialquiz.model.question.Question
import com.desiredsoftware.socialquiz.utils.generateQuestion
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenterScope
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import javax.inject.Inject

@InjectViewState
class QuestionShowingPresenter @Inject constructor(
    private var firebaseRepository: FirebaseRepository,
    private var context: Context
) : MvpPresenter<QuestionShowingPresenter.IQuestionView>() {

    var mCategoriesQuestions: List<Any?>? = null
    var mQuestionCategoryId: String = ""


    fun initUI() {

    }

    fun getNextQuestion(): Question {
        //val questions = getCategoryQuestions(mQuestionCategoryId)
        return generateQuestion()
    }

    fun getCategoryQuestions(mQuestionCategoryId: String) {
        presenterScope.launch {
            val questions = firebaseRepository.getQuestionsArrayByCategory(mQuestionCategoryId)
        }
    }

    fun showQuestion() {
        val questionToShow = getNextQuestion()
        questionToShow.mAnswers?.let {
            viewState.showAnswers(context, it)
        }

        viewState.showVideoQuestion(context, questionToShow.mQuestionBody)
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface IQuestionView : MvpView {
        fun showVideoQuestion(context: Context, questionBodyVideoUri: String)
        fun showAnswers(context: Context, answers: List<Answer>)
    }
}