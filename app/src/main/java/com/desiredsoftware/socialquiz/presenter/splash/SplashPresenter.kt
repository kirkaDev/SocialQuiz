package com.desiredsoftware.socialquiz.presenter.splash

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenterScope
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import javax.inject.Inject


class SplashPresenter @Inject constructor(
) : MvpPresenter<SplashPresenter.ISplashView>() {

    fun initUI() {
        presenterScope.launch {
            viewState.showAnimation(true)
            delay(1000L)
            viewState.showAnimation(false)
            viewState.openAuthScreen()
        }
    }

    @StateStrategyType(OneExecutionStateStrategy::class)
    interface ISplashView : MvpView {
        fun showAnimation(showAnimation: Boolean)
        fun openAuthScreen()
    }
}