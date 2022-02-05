package com.desiredsoftware.socialquiz.presenter.auth

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenterScope
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import javax.inject.Inject


class AuthPresenter @Inject constructor(
    private var context: Context
) : MvpPresenter<AuthPresenter.IAuthView>() {

    var signInLauncher : ActivityResultLauncher<Intent>? = null

    fun initUI() {
        checkLoginAndShowMainController()
    }

    fun checkLoginAndShowMainController(){
        val googleProvider = AuthUI.IdpConfig.GoogleBuilder().build()

        val availableProviders : MutableList<AuthUI.IdpConfig> = mutableListOf()
        availableProviders.add(googleProvider)

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setIsSmartLockEnabled(false)
            .setAvailableProviders(availableProviders)
            .build()

        presenterScope.launch {
            viewState.checkLogin(signInLauncher, signInIntent)
        }
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface IAuthView : MvpView {
        fun checkLogin(signInLauncher: ActivityResultLauncher<Intent>?, signInIntent: Intent)
    }
}