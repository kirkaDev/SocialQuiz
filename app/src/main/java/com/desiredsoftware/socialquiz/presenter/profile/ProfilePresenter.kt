package com.desiredsoftware.socialquiz.presenter.profile

import android.content.Context
import android.util.Log
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.profile.Profile
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
                if (isUserProfileExists(uid)) {
                    currentUser?.let {
                        bindProfile(it)
                    }
                } else {
                    createProfile(uid)
                    currentUser?.let {
                        bindProfile(it)
                    } ?: run {
                        isUserProfileExists(uid)
                        currentUser?.let {
                            bindProfile(it)
                        }
                    }
                }
            } ?: run {
                viewState.showError(context.getString(R.string.auth_error))
            }
        }
    }

    private fun bindProfile(profile: Profile) {
        viewState.showAvatar(profile.avatarURI)
        viewState.showNickName(profile.nickName)
        viewState.showScore(profile.score.toString())
        viewState.showRole(profile.role.toString())
        viewState.showAbout(profile.about)
        viewState.showInstagram(profile.instagram)
        viewState.showTikTok(profile.tiktok)
        viewState.showAccountType(profile.accountType.toString())
    }

    private suspend fun isUserProfileExists(uid: String): Boolean {
        currentUser = firebaseRepository.getProfile(uid)
        return currentUser != null
    }

    private fun createProfile(uid: String) {
        firebaseRepository.createProfile(uid)
    }

    fun commitChanges(field: String, newValue: String){
        when (field){
            Profile.FIELD_NICK_NAME ->{
                currentUser?.nickName = newValue
                currentUser?.let { firebaseRepository.commitProfile(it) }
            }
            Profile.FIELD_ABOUT ->{
                currentUser?.about = newValue
                currentUser?.let { firebaseRepository.commitProfile(it) }
            }
            Profile.FIELD_INSTAGRAM ->{
                currentUser?.instagram = newValue
                currentUser?.let { firebaseRepository.commitProfile(it) }
            }
            Profile.FIELD_TIK_TOK ->{
                currentUser?.tiktok = newValue
                currentUser?.let { firebaseRepository.commitProfile(it) }
            }
            else ->{
            }
        }

    }

    fun uploadAvatarToStorage(avatarPath: String) {
        presenterScope.launch {
            var avatarFullRef: StorageReference? = null
            firebaseUser?.let { user ->
                getExtension(avatarPath)?.let { extension ->
                    val avatarFileName = user.uid + extension
                    avatarFullRef = firebaseStorageRef.child(getPath() + avatarFileName)

                    val stream = FileInputStream(File(avatarPath))

                    val uploadTask = avatarFullRef?.putStream(stream)
                    uploadTask?.addOnFailureListener {
                        Log.d("upload", "upload fail, cause: ${it.message}")
                    }?.addOnSuccessListener { taskSnapshot ->
                        Log.d(
                            "upload",
                            "upload success, bytesTransferred = ${taskSnapshot.bytesTransferred}"
                        )
                    }
                }
                    ?: run {
                        viewState.showError("File is broken or is not an image")
                    }
            } ?: run {
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

    private fun getPath(): String {
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