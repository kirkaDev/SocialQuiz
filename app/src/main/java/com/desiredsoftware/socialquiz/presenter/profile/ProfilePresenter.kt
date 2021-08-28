package com.desiredsoftware.socialquiz.presenter.profile

import android.content.Context
import com.desiredsoftware.socialquiz.model.profile.Profile
import com.desiredsoftware.socialquiz.utils.generateProfile
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
    private var context: Context,
) : MvpPresenter<ProfilePresenter.IProfileView>() {

    val profile = generateProfile()

    fun initUI(){
        presenterScope.launch {
            viewState.showAvatar(profile.avatarURI)
            viewState.showPropertiesList(profile.propertiesList)
        }
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface IProfileView : MvpView {
        fun showAvatar(avatarUrl: String)
        fun showPropertiesList(list: List<Profile.ProfileProperty>)
    }
}