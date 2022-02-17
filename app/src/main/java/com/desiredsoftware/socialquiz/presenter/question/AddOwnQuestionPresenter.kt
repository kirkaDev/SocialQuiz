package com.desiredsoftware.socialquiz.presenter.question

import android.content.Context
import android.util.Log
import com.desiredsoftware.socialquiz.data.model.category.Category
import com.desiredsoftware.socialquiz.data.model.question.Answer
import com.desiredsoftware.socialquiz.data.model.question.Question
import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
import com.desiredsoftware.socialquiz.view.IError
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenterScope
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

@InjectViewState
class AddOwnQuestionPresenter @Inject constructor(
    private var firebaseRepository: FirebaseRepository,
    private val firebaseUser: FirebaseUser?,
    private val firebaseStorageRef: StorageReference,
    private var context: Context
) : MvpPresenter<AddOwnQuestionPresenter.IAddOwnQuestionView>() {

    val answersCount = 4

    var proposedQuestion: Question = Question()
    var answers: MutableList<Answer> = MutableList(answersCount) {
        Answer("", false)
    }
    var videoAbsolutePath: String = ""

    fun initVideoUrl(videoAbsolutePath: String) {
        this.videoAbsolutePath = videoAbsolutePath
        presenterScope.launch {
            viewState.showVideoThumbnail(videoAbsolutePath)
        }
    }

    fun initAnswersList() {
        presenterScope.launch {
            viewState.initAnswersList(answers)
        }
    }

    fun initCategoriesSpinner() {
        presenterScope.launch {
            viewState.initCategoriesSpinner(
                firebaseRepository.getCategories()
            )
        }
    }

    fun uploadVideoToStorage(videoAbsolutePath: String) {
        presenterScope.launch {
            var videoFullRef: StorageReference? = null
            firebaseUser?.let { user ->
                val videoFileName = user.uid + ".mp4"
                videoFullRef = firebaseStorageRef.child(VIDEO_PATH + videoFileName)

                val stream = FileInputStream(File(videoAbsolutePath))

                val uploadTask = videoFullRef?.putStream(stream)
                uploadTask?.addOnFailureListener {
                    Log.d("upload", "upload fail, cause: ${it.message}")
                }?.addOnSuccessListener { taskSnapshot ->
                    Log.d(
                        "upload",
                        "upload success, bytesTransferred = ${taskSnapshot.bytesTransferred}"
                    )
                }
            } ?: run {
                viewState.showError("Can't get profile's data")
            }
        }
    }

    fun buildQuestion() {
        val unixTimestamp = "_" + System.currentTimeMillis() / 1000
        proposedQuestion.apply {
            firebaseUser?.let{ firebaseUser ->
                // Restriction for document name is 1024KiB (1024 bit)
                questionId = firebaseUser.uid + unixTimestamp
                // categoryId - should be set from spinner in controller already
                answerVariants = answers

                questionBody = firebaseUser.uid + unixTimestamp
                questionAuthorUid = firebaseUser.uid
                questionType = Question.Companion.QUESTION_TYPE.VIDEO
            } ?: kotlin.run {
                Log.d("buildQuestion", "buildQuestion: firebaseUser is null. " +
                        "Question must be stopped with contract checking")
            }
        }
    }

    fun checkQuestionContract(): Boolean {
        var rightAnswersCount = 0

        if (proposedQuestion.categoryId.isEmpty()) {
            Log.d("checkQuestionContract", "checkQuestionContract: categoryId is empty")
            return false
        }
        proposedQuestion.answerVariants.forEachIndexed() { index, answer ->
            if (answer.answer.isEmpty()) {
                Log.d("checkQuestionContract", "answer with index=$index is empty")
                return false
            } else {
                if (answer.isCorrect) rightAnswersCount++
            }
        }
        if (rightAnswersCount != 1) {
            Log.d("checkQuestionContract", "right answers count = $rightAnswersCount")
            return false
        }

        if (proposedQuestion.questionAuthorUid.isNullOrEmpty()) {
            Log.d(
                "checkQuestionContract",
                "firebaseUser.uid = ${proposedQuestion.questionAuthorUid}"
            )
            return false
        }
        return true
    }

    fun sendQuestion() {

    }

    companion object {
        val VIDEO_PATH = "video/"
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface IAddOwnQuestionView : MvpView, IError {
        fun initCategoriesSpinner(categoriesList: List<Category>)
        fun initAnswersList(answersList: MutableList<Answer>)
        fun showVideoThumbnail(videoAbsolutePath: String)
    }
}