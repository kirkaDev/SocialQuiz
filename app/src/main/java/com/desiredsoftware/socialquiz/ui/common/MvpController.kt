package com.desiredsoftware.socialquiz.ui.common

import android.os.Bundle
import android.view.View
import com.bluelinelabs.conductor.Controller
import moxy.MvpDelegate
import moxy.MvpDelegateHolder

abstract class MvpController : Controller, MvpDelegateHolder {

    private val mMvpDelegate = MvpDelegate<MvpController>(this)

    override fun getMvpDelegate(): MvpDelegate<*> {
        return mMvpDelegate
    }

    constructor() : super() {
        inject()

        mvpDelegate.onCreate()
    }

    constructor(args: Bundle) : super(args) {
        inject()
        mvpDelegate.onCreate(args)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)

        mvpDelegate.onAttach()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)

        mvpDelegate.onDetach()
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)

        mvpDelegate.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()

        mvpDelegate.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        mvpDelegate.onSaveInstanceState(outState)
    }

    open fun inject() {
        // Inject fields if needed
    }
}