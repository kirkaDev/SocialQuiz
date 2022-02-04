package com.desiredsoftware.socialquiz.ui.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.databinding.ViewControllerProfileBinding
import com.desiredsoftware.socialquiz.di.App
import com.desiredsoftware.socialquiz.presenter.profile.ProfilePresenter
import com.desiredsoftware.socialquiz.ui.common.MvpController
import com.desiredsoftware.socialquiz.utils.PathUtils
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

    private var _binding: ViewControllerProfileBinding? = null
    private val binding get() = _binding!!

    private var avatarUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        _binding = ViewControllerProfileBinding.inflate(inflater, container, false)
        mPresenter.initUI()

        binding.imageViewAvatar.setOnClickListener{
            startGalleryIntent()
        }

        return binding.root
    }

    private fun startGalleryIntent(){
        val galleryIntent = Intent()
        galleryIntent.type = "image/*"
        galleryIntent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(galleryIntent,
            resources?.getString(R.string.choose_your_avatar)), UPLOAD_AVATAR_TO_PROFILE_REQUEST_CODE)
    }

    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showAvatar(avatarUrl: String) {
        Log.d("image URI", "show avatar called")
        view?.context?.let {
            Glide.with(it)
                .load(avatarUrl)
                .error(it.resources.getDrawable(R.drawable.ic_profile_white_24dp))
                .placeholder(it.resources.getDrawable(R.drawable.ic_profile_white_24dp))
                .into(binding.imageViewAvatar)
        }
    }

    override fun showNickName(nickName: String) {
        binding.nicknameEditText.hint = nickName
    }

    override fun showScore(score: String) {
        binding.scoreEditText.hint = score
    }

    override fun showRole(role: String) {
        binding.roleEditText.hint = role
    }

    override fun showAbout(about: String) {
        binding.aboutEditText.hint = about
    }

    override fun showInstagram(instagram: String) {
        binding.instagramEditText.hint = instagram
    }

    override fun showTikTok(tiktok: String) {
        binding.tikTokEditText.hint = tiktok
    }

    override fun showAccountType(accountType: String) {
        binding.accountTypeEditText.hint = accountType
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){
            UPLOAD_AVATAR_TO_PROFILE_REQUEST_CODE ->{
                if (resultCode==RESULT_OK
                    && data!=null
                    && data.data!=null)
                {
                    avatarUri = data.data

                    val absolutePath = PathUtils.getPath(activity, avatarUri)
                    mPresenter.uploadAvatarToStorage(absolutePath)
                }
            }
        }
    }

    companion object{
        val UPLOAD_AVATAR_TO_PROFILE_REQUEST_CODE= 57875
    }

}