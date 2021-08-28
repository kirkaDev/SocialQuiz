package com.desiredsoftware.socialquiz.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.di.App
import com.desiredsoftware.socialquiz.model.profile.Profile
import com.desiredsoftware.socialquiz.presenter.profile.ProfilePresenter
import com.desiredsoftware.socialquiz.ui.common.MvpController
import com.desiredsoftware.socialquiz.ui.components.ProfilePropertiesAdapter
import com.squareup.picasso.Picasso
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

    private lateinit var avatarImageView : ImageView
    private lateinit var profilePropertiesList : RecyclerView

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

    override fun showAvatar(avatarUrl: String) {
        Picasso.get().load(avatarUrl)
            .resize(200, 200)
            .centerCrop()
            .into(avatarImageView)
    }

    override fun showPropertiesList(list: List<Profile.ProfileProperty>) {
        profilePropertiesList.adapter = ProfilePropertiesAdapter(list)
        profilePropertiesList.layoutManager = LinearLayoutManager(activity?.applicationContext)
    }

}