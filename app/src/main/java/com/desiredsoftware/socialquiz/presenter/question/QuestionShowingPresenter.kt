package com.desiredsoftware.socialquiz.presenter.question

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.question.Answer
import com.desiredsoftware.socialquiz.data.model.question.Question
import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
import com.desiredsoftware.socialquiz.di.SharedPreferencesModule
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
import javax.inject.Named


@InjectViewState
class QuestionShowingPresenter @Inject constructor(
    private var firebaseRepository: FirebaseRepository,
    private var firestoreStorage: StorageReference,

    @Named(SharedPreferencesModule.SOLVED_QUESTIONS_PREFS)
    private var solvedQuestionsPrefs: SharedPreferences,
    private var context: Context
) : MvpPresenter<QuestionShowingPresenter.IQuestionView>() {

    var questionCategoryId: String = ""
    var currentQuestion: Question? = null

    fun initUI() {
        presenterScope.launch {
            Log.d("question", "called initUI()")
            showQuestion()
        }
    }

    private suspend fun getNextQuestion(): Question? {
        getCategoryQuestions(questionCategoryId).let {
            if (it.isNotEmpty()) {
                getUnsolvedQuestions(it)?.let{ unsolvedQuestions ->
                    if (unsolvedQuestions.isNotEmpty()){
                        currentQuestion = unsolvedQuestions.random()
                        return currentQuestion
                    }
                    return null
                }
            } else
                null
        } ?: kotlin.run {
            return null
        }
    }

    private fun getUnsolvedQuestions(questions: List<Question>): List<Question>? {
        val solvedQuestionsIds = solvedQuestionsPrefs.getStringSet(
            SharedPreferencesModule.SOLVED_QUESTIONS_PREFS,
            emptySet()
        )

        return if (solvedQuestionsIds.isNullOrEmpty()) {
            questions
        } else {
            questions.filter {
                !solvedQuestionsIds.contains(it.questionId)
            }
        }
    }

    fun saveSolvedQuestion(){

        val solvedQuestionsIds = solvedQuestionsPrefs.getStringSet(
            SharedPreferencesModule.SOLVED_QUESTIONS_PREFS,
            emptySet()
        )

        val newStrSet: MutableSet<String> = HashSet()
        newStrSet.add(currentQuestion?.questionId!!)
        newStrSet.addAll(solvedQuestionsIds as Collection<String>)

        currentQuestion?.let{
            solvedQuestionsPrefs.edit().putStringSet(
                SharedPreferencesModule.SOLVED_QUESTIONS_PREFS,
                newStrSet
            ).commit()
        }
    }

    private suspend fun getCategoryQuestions(questionCategoryId: String): List<Question> {
        return firebaseRepository.getQuestionsOfCategory(
            questionCategoryId,
            Question.Companion.IS_APPROVED.APPROVED
        )
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
            viewState.showError(context.getString(R.string.all_question_solved))
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