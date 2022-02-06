package com.desiredsoftware.socialquiz.presenter.question

import android.content.Context
import android.util.Log
import com.desiredsoftware.socialquiz.data.model.question.Answer
import com.desiredsoftware.socialquiz.data.model.question.Question
import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
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
    var questionCategoryId: String = ""

    fun initUI() {
        presenterScope.launch {
            Log.d("question", "called initUI()")
            showQuestion()
        }
    }

    private suspend fun getNextQuestion(): Question {
        Log.d("question", "called getNextQuestion")
        val questions = getCategoryQuestions(questionCategoryId)
        Log.d("question", "called getNextQuestion, requesting first Question")
        return questions.first()
    }

    private suspend fun getCategoryQuestions(mQuestionCategoryId: String): List<Question> {
        Log.d("question", "called getCategoryQuestions()")
        val questions = firebaseRepository.getQuestionsOfCategory(mQuestionCategoryId)
        Log.d("question", "called getCategoryQuestions(), received questions = $questions")
        return questions
    }

    private suspend fun showQuestion() {
        Log.d("question", "called showQuestion()")
        val questionToShow = getNextQuestion()
        Log.d("question", "called showQuestion(), received question for show")
        questionToShow.answerVariants?.let {
            viewState.showAnswers(context, it)
        }

        when (questionToShow.questionType) {
            Question.Companion.QUESTION_TYPE.VIDEO -> {
                viewState.showVideoQuestion(context, questionToShow.questionBody)
            }
            Question.Companion.QUESTION_TYPE.TEXT -> {
                viewState.showTextQuestion(context, questionToShow.questionBody)
            }
            else -> {

            }
        }
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface IQuestionView : MvpView {
        fun showVideoQuestion(context: Context, questionBodyVideoUri: String)
        fun showTextQuestion(context: Context, questionBodyVideoUri: String)
        fun showAnswers(context: Context, answers: List<Answer>)
    }
}