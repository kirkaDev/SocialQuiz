package com.desiredsoftware.socialquiz.presenter.profile

import android.content.Context
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
import com.desiredsoftware.socialquiz.model.profile.Profile
import com.desiredsoftware.socialquiz.view.IError
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenterScope
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import javax.inject.Inject

@InjectViewState
class ProfilePresenter @Inject constructor(
    private val context: Context,
    private val firebaseRepository: FirebaseRepository,
    private val firebaseUser: FirebaseUser?,
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

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface IProfileView : MvpView, IError {
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