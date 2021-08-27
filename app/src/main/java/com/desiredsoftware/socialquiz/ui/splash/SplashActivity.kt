package com.desiredsoftware.socialquiz.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import com.desiredsoftware.socialquiz.MainActivity
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.presenter.splash.SplashPresenter
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class SplashActivity : MvpAppCompatActivity(), SplashPresenter.ISplashView {

    private val mPresenter by moxyPresenter { SplashPresenter() }

    lateinit var mProgressBar: ProgressBar
    lateinit var mLoadingTextView: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_controller_splash)

        mProgressBar = findViewById(R.id.progressBar)
        mLoadingTextView = findViewById(R.id.loadingTextView)

        mPresenter.initUI()
    }

    override fun showAnimation(showAnimation: Boolean) {
        mProgressBar.isVisible = showAnimation
        mLoadingTextView.isVisible = showAnimation
    }

    override fun openAuthScreen() {
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }
}