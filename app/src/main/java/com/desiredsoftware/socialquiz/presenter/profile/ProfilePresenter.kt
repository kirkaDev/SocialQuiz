package com.desiredsoftware.socialquiz.presenter.profile

import android.content.Context
import android.util.Log
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
import com.desiredsoftware.socialquiz.model.profile.Profile
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
import moxy.viewstate.strategy.alias.OneExecution
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

@InjectViewState
class ProfilePresenter @Inject constructor(
    private val context: Context,
    private val firebaseRepository: FirebaseRepository,
    private val firebaseUser: FirebaseUser?,
    private val firebaseStorageRef: StorageReference,
) : MvpPresenter<ProfilePresenter.IProfileView>() {

    var currentUser: Profile? = null

    fun initUI() {
        presenterScope.launch {
            firebaseUser?.uid?.let { uid ->
                firebaseRepository.getProfile(uid)?.let {
                    currentUser = it
                    viewState.showAvatar(it.avatarURI)
                    viewState.showNickName(it.nickName)
                    viewState.showScore(it.score.toString())
                    viewState.showRole(it.role)
                    viewState.showAbout(it.about)
                    viewState.showInstagram(it.instagram)
                    viewState.showTikTok(it.tiktok)
                    viewState.showAccountType(it.accountType)
                } ?: run {
                    viewState.showError(context.getString(R.string.profile_error))
                }
            } ?: run {
                viewState.showError(context.getString(R.string.profile_error))
            }
        }
    }

    fun uploadAvatarToStorage(avatarPath: String) {
        presenterScope.launch {
            var avatarFullRef: StorageReference? = null
            firebaseUser?.let { user ->
                getExtension(avatarPath)?.let{ extension ->
                    val avatarFileName = user.uid + extension
                    avatarFullRef = firebaseStorageRef.child(getPath() + avatarFileName)

                    val stream = FileInputStream(File(avatarPath))

                    val uploadTask = avatarFullRef?.putStream(stream)
                    uploadTask?.addOnFailureListener {
                        Log.d("upload", "upload fail, cause: ${it.message}")
                    }?.addOnSuccessListener { taskSnapshot ->
                        Log.d("upload", "upload success, bytesTransferred = ${taskSnapshot.bytesTransferred}")
                    }
                }
                    ?: run{
                        viewState.showError("File is broken or is not an image")
                    }
            } ?: run{
                viewState.showError("Can't get profile's data")
            }
        }
    }

    private fun getExtension(pathWithExtension: String): String? {
        return when {
            pathWithExtension.contains(".jpg") ->
                ".jpg"

            pathWithExtension.contains(".jpeg") ->
                ".jpeg"

            pathWithExtension.contains(".png") ->
                ".png"

            else -> null
        }
    }

    private fun getPath(): String{
        return "avatars/"
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface IProfileView : MvpView, IError {
        @OneExecution
        fun showAvatar(avatarUri: String)
        fun showNickName(nickName: String)
        fun showScore(score: String)
        fun showRole(role: String)
        fun showAbout(about: String)
        fun showInstagram(instagram: String)
        fun showTikTok(tiktok: String)
        fun showAccountType(accountType: String)
    }
}