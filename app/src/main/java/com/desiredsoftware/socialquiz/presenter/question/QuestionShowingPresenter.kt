package com.desiredsoftware.socialquiz.presenter.question

import android.content.Context
import android.util.Log
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.question.Answer
import com.desiredsoftware.socialquiz.data.model.question.Question
import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
import com.desiredsoftware.socialquiz.view.IError
import com.google.firebase.storage.StorageReference
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
    private var firestoreStorage: StorageReference,
    private var context: Context
) : MvpPresenter<QuestionShowingPresenter.IQuestionView>() {

    var questionCategoryId: String = ""

    fun initUI() {
        presenterScope.launch {
            Log.d("question", "called initUI()")
            showQuestion()
        }
    }

    // TODO: Here should be algorithm to find a new question
    private suspend fun getNextQuestion(): Question? {
        getCategoryQuestions(questionCategoryId).let{
            return if (it.isNotEmpty()) {
                it.random()
            } else
                null
        }
    }

    private suspend fun getCategoryQuestions(mQuestionCategoryId: String): List<Question> {
        return firebaseRepository.getQuestionsOfCategory(mQuestionCategoryId)
    }

    private suspend fun showQuestion() {
        getNextQuestion()?.let { questionToShow ->
            questionToShow.answerVariants.let { answers ->
                viewState.showAnswers(context, answers)
            }
            when (questionToShow.questionType) {
                Question.Companion.QUESTION_TYPE.VIDEO -> {
                    val dateRef = firestoreStorage.child(questionToShow.questionBody)
                    dateRef.downloadUrl.addOnSuccessListener {
                        viewState.showVideoQuestion(context, it.toString())
                    }
                }
                Question.Companion.QUESTION_TYPE.TEXT -> {
                    viewState.showTextQuestion(context, questionToShow.questionBody)
                }
            }
        } ?: kotlin.run {
            viewState.showError(context.getString(R.string.get_question_error))
            viewState.popCurrentController()
        }
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface IQuestionView : MvpView, IError {
        fun showVideoQuestion(context: Context, questionBodyVideoUri: String)
        fun showTextQuestion(context: Context, questionBodyVideoUri: String)
        fun popCurrentController()
        fun showAnswers(context: Context, answers: List<Answer>)
    }
}