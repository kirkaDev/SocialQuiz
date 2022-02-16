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

    fun initVideoUrl(videoAbsolutePath: String){
        this.videoAbsolutePath = videoAbsolutePath
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

    fun uploadVideoToStorage(videoAbsolutePath: String){
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

    companion object{
        val VIDEO_PATH = "video/"
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface IAddOwnQuestionView : MvpView, IError {
        fun initCategoriesSpinner(categoriesList: List<Category>)
        fun initAnswersList(answersList: MutableList<Answer>)
    }
}