package com.desiredsoftware.socialquiz.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.AppCompatButton
import com.bluelinelabs.conductor.RouterTransaction
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.di.App
import com.desiredsoftware.socialquiz.presenter.auth.AuthPresenter
import com.desiredsoftware.socialquiz.ui.categories.CategoriesController
import com.desiredsoftware.socialquiz.ui.common.MvpController
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class AuthController : MvpController, AuthPresenter.IAuthView {

    constructor() : super()
    constructor (signInLauncher: ActivityResultLauncher<Intent>) {
        mPresenter.signInLauncher = signInLauncher
    }

    @Inject
    @InjectPresenter
    lateinit var mPresenter: AuthPresenter

    @ProvidePresenter
    fun provides() = mPresenter

    override fun inject() {
        super.inject()
        App.appComponent.inject(this)
    }

    lateinit var mAuthButton: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.view_controller_auth, container, false)

        mAuthButton = view.findViewById(R.id.authButton)
        mAuthButton.setOnClickListener {
            mPresenter.checkLoginAndShowMainController()
        }

        mPresenter.initUI()

        return view
    }

    override fun checkLogin(
        signInLauncher: ActivityResultLauncher<Intent>?,
        signInIntent: Intent
    ) {
        signInLauncher?.launch(signInIntent)
    }

    override fun openMainController() {
        router.pushController(RouterTransaction.with(CategoriesController()))
    }
}