package com.desiredsoftware.socialquiz.ui.profile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
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
import com.desiredsoftware.socialquiz.utils.FilePathUtils
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

        binding.imageViewAvatar.setOnClickListener {
            checkPermissions()
        }

        return binding.root
    }

    private fun startGalleryIntent() {
        val galleryIntent = Intent()
        galleryIntent.type = "image/*"
        galleryIntent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(
            Intent.createChooser(
                galleryIntent,
                resources?.getString(R.string.choose_your_avatar)
            ), UPLOAD_AVATAR_TO_PROFILE_REQUEST_CODE
        )
    }

    private fun checkPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), PERMISSIONS_REQUEST_CODE
        )
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    startGalleryIntent()
                } else {
                    Toast.makeText(activity, R.string.permissions_failed, Toast.LENGTH_LONG).show()
                }
            }
            else -> {
            }
        }
    }

    override fun showAbout(about: String) {
        if (about.isEmpty())
            binding.aboutEditText.hint = resources?.getString(R.string.empty)
        else binding.aboutEditText.hint = about
    }

    override fun showInstagram(instagram: String) {
        if (instagram.isEmpty())
            binding.instagramEditText.hint = resources?.getString(R.string.empty)
        else binding.instagramEditText.hint = instagram
    }

    override fun showTikTok(tiktok: String) {
        if (tiktok.isEmpty())
            binding.tikTokEditText.hint = resources?.getString(R.string.empty)
        else binding.tikTokEditText.hint = tiktok
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

        when (requestCode) {
            UPLOAD_AVATAR_TO_PROFILE_REQUEST_CODE -> {
                if (resultCode == RESULT_OK
                    && data != null
                    && data.data != null
                ) {
                    avatarUri = data.data

                    val absolutePath = FilePathUtils.getPath(activity, avatarUri)
                    mPresenter.uploadAvatarToStorage(absolutePath)
                }
            }
        }
    }

    companion object {
        val UPLOAD_AVATAR_TO_PROFILE_REQUEST_CODE = 57875
        const val PERMISSIONS_REQUEST_CODE = 48573
    }

}