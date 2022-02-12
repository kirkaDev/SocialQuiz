package com.desiredsoftware.socialquiz.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler
import com.desiredsoftware.socialquiz.MainActivity
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.di.App
import com.desiredsoftware.socialquiz.presenter.splash.SplashPresenter
import com.desiredsoftware.socialquiz.ui.common.MvpController
import com.desiredsoftware.socialquiz.ui.question.AddOwnQuestionController
import com.firebase.ui.auth.AuthUI
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class SplashController: MvpController, SplashPresenter.ISplashView {
    constructor() : super()

    @Inject
    @InjectPresenter
    lateinit var presenter: SplashPresenter

    @ProvidePresenter
    fun provides() = presenter

    override fun inject() {
        super.inject()
        App.appComponent.inject(this)
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var loadingTextView: AppCompatTextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        val view: View =
            inflater.inflate(R.layout.view_controller_splash, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        loadingTextView = view.findViewById(R.id.loadingTextView)
        presenter.initUI()
        return view
    }

    override fun showAnimation(showAnimation: Boolean) {
        progressBar.isVisible = showAnimation
        loadingTextView.isVisible = showAnimation
    }

    override fun runCategoriesScreen() {
        // TODO: Return Categories controller to default
        // Made for dev process
        router?.replaceTopController(RouterTransaction.with(AddOwnQuestionController())
        //router?.replaceTopController(RouterTransaction.with(CategoriesController())
        .pushChangeHandler(VerticalChangeHandler())
        .popChangeHandler(VerticalChangeHandler()))
    }

    override fun runGetAuthIntent() {
        val signInLauncher : ActivityResultLauncher<Intent>? = MainActivity.loginLauncher

        val googleProvider = AuthUI.IdpConfig.GoogleBuilder().build()
        val availableProviders : MutableList<AuthUI.IdpConfig> = mutableListOf()
        availableProviders.add(googleProvider)

        val getAuthIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setIsSmartLockEnabled(false)
            .setAvailableProviders(availableProviders)
            .build()

        signInLauncher?.launch(getAuthIntent)
    }
}
