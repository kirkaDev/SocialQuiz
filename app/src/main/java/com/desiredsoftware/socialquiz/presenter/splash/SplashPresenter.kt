package com.desiredsoftware.socialquiz.presenter.splash

import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenterScope
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import javax.inject.Inject


class SplashPresenter @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val firebaseUser: FirebaseUser?
) : MvpPresenter<SplashPresenter.ISplashView>() {

    fun initUI() {
        presenterScope.launch {
            viewState.showAnimation(true)
            delay(500)
            firebaseUser?.let {
                if (checkUserProfileExists(it.uid))
                {
                    viewState.showAnimation(false)
                    viewState.runCategoriesScreen()
                }
                else{
                    createProfile(it.uid)
                    viewState.showAnimation(false)
                    viewState.runCategoriesScreen()
                }
            } ?: run {
                viewState.runGetAuthIntent()
            }
        }
    }

    private suspend fun checkUserProfileExists(uid: String): Boolean{
        return firebaseRepository.getProfile(uid) != null
    }

    private fun createProfile(uid: String){
        firebaseRepository.createProfile(uid)
    }

    @StateStrategyType(OneExecutionStateStrategy::class)
    interface ISplashView : MvpView {
        fun showAnimation(showAnimation: Boolean)
        fun runCategoriesScreen()
        fun runGetAuthIntent()
    }
}