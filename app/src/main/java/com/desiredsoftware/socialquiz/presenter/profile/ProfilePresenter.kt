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

    fun initUI() {
        presenterScope.launch {
            firebaseUser?.uid?.let { uid ->
                firebaseRepository.getProfile(uid)?.let {
                    viewState.showUserInfo(it)
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
        fun showUserInfo(profile: Profile)
    }
}