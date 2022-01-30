package com.desiredsoftware.socialquiz.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.di.App
import com.desiredsoftware.socialquiz.model.profile.Profile
import com.desiredsoftware.socialquiz.presenter.profile.ProfilePresenter
import com.desiredsoftware.socialquiz.ui.common.MvpController
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class ProfileController : MvpController(), ProfilePresenter.IProfileView {

    @Inject
    @InjectPresenter
    lateinit var mPresenter: ProfilePresenter

    @ProvidePresenter
    fun provides() = mPresenter

    override fun inject() {
        super.inject()
        App.appComponent.inject(this)
    }

    private lateinit var avatarImageView: ImageView
    private lateinit var profilePropertiesList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.view_controller_profile, container, false)

        avatarImageView = view.findViewById(R.id.imageViewAvatar)
        profilePropertiesList = view.findViewById(R.id.recyclerViewProfileProperties)

        mPresenter.initUI()

        return view
    }

    override fun showUserInfo(profile: Profile) {
        showAvatar(profile.avatarURI)
    }

    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    private fun showAvatar(avatarUrl: String) {
        view?.context?.let {
            Glide.with(it).load(avatarUrl).into(avatarImageView)
        }
    }
}