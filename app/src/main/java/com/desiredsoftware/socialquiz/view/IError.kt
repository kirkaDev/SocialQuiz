package com.desiredsoftware.socialquiz.view

import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface IError {
    fun showError(message: String)
}