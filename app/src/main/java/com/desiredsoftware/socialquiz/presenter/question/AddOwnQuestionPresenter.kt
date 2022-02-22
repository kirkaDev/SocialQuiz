package com.desiredsoftware.socialquiz.presenter.question

import android.content.Context
import android.util.Log
import com.desiredsoftware.socialquiz.R
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

    // Path get from intent after recording
    var videoAbsolutePath = String()

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

    fun sendQuestion() {
        presenterScope.launch {
            val videoFileName: String = proposedQuestion.questionBody
            val videoAbsolutePath: String = videoAbsolutePath

            if (videoAbsolutePath.isNotEmpty()) {
                var videoFullRef: StorageReference? = null

                firebaseUser?.let { user ->
                    videoFullRef =
                        firebaseStorageRef.child(VIDEO_PATH + user.uid + "/" + videoFileName)

                    val stream = FileInputStream(File(videoAbsolutePath))
                    viewState.showError(context.resources.getString(R.string.uploading_question))
                    val uploadTask = videoFullRef?.putStream(stream)
                    uploadTask?.addOnFailureListener {
                        viewState.showError(context.resources.getString(R.string.upload_video_error))
                        Log.d("upload", "upload fail, cause: ${it.message}")
                    }?.addOnSuccessListener { taskSnapshot ->
                        Log.d(
                            "upload",
                            "upload success, bytesTransferred = ${taskSnapshot.bytesTransferred}"
                        )
                        viewState.showError(context.resources.getString(R.string.upload_video_success))
                        proposedQuestion.questionBody = taskSnapshot.storage.path

                        firebaseRepository.uploadQuestion(proposedQuestion)
                    }
                } ?: run {
                    viewState.showError("Can't get profile's data")
                }
            } else {
                viewState.showError(context.resources.getString(R.string.video_not_choosen))
            }
        }
    }

    fun buildQuestion() {
        val unixTimestamp = "_" + System.currentTimeMillis() / 1000
        proposedQuestion.apply {
            firebaseUser?.let { firebaseUser ->
                // Restriction for document name is 1024KiB (1024 bit)
                questionId = firebaseUser.uid + unixTimestamp
                // categoryId - should be set from spinner in controller already
                answerVariants = answers
                questionBody = firebaseUser.uid + unixTimestamp
                questionAuthorUid = firebaseUser.uid
                questionType = Question.Companion.QUESTION_TYPE.VIDEO
            } ?: kotlin.run {
                Log.d(
                    "buildQuestion", "buildQuestion: firebaseUser is null. " +
                            "Question must be stopped with contract checking"
                )
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
            viewState.showError(context.resources.getString(R.string.correct_variant_not_choosen))
            return false
        }

        if (proposedQuestion.questionBody.isEmpty()) {
            Log.d("checkQuestionContract", "questionBody is empty")
            return false
        }

        if (proposedQuestion.questionAuthorUid.isEmpty()) {
            Log.d(
                "checkQuestionContract",
                "firebaseUser.uid = ${proposedQuestion.questionAuthorUid}"
            )
            return false
        }

        // If this instruction runs then all properties is filled and probably, ok
        Log.d("checkQuestionContract", "contract is OK!")
        return true
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