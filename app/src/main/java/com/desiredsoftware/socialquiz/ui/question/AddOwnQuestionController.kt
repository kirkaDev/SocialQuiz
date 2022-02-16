package com.desiredsoftware.socialquiz.ui.question

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore.INTENT_ACTION_VIDEO_CAMERA
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.category.Category
import com.desiredsoftware.socialquiz.data.model.question.Answer
import com.desiredsoftware.socialquiz.databinding.ViewControllerAddOwnQuestionBinding
import com.desiredsoftware.socialquiz.di.App
import com.desiredsoftware.socialquiz.presenter.question.AddOwnQuestionPresenter
import com.desiredsoftware.socialquiz.ui.common.MvpController
import com.desiredsoftware.socialquiz.ui.profile.ProfileController
import com.desiredsoftware.socialquiz.ui.question.adapters.OwnAnswerVariantsAdapter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class AddOwnQuestionController : MvpController(), AddOwnQuestionPresenter.IAddOwnQuestionView {

    private var _binding: ViewControllerAddOwnQuestionBinding? = null
    private val binding get() = _binding!!


    @Inject
    @InjectPresenter
    lateinit var presenter: AddOwnQuestionPresenter

    @ProvidePresenter
    fun provides() = presenter

    override fun inject() {
        super.inject()
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        _binding = ViewControllerAddOwnQuestionBinding.inflate(inflater, container, false)

        presenter.initCategoriesSpinner()
        presenter.initAnswersList()

        binding.addVideoButton.setOnClickListener {
            checkPermissionForVideoUploading()
        }

        return binding.root
    }

    override fun initCategoriesSpinner(categoriesList: List<Category>) {
        binding.categoriesSpinner.apply {
            adapter = ArrayAdapter(
                activity?.applicationContext!!,
                R.layout.item_spinner,
                categoriesList.map { category ->
                    category.categoryName
                }
            )

            onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        Log.d(
                            "CREATE_QUESTION",
                            "category with id=${categoriesList[position].id} selected"
                        )
                        presenter.proposedQuestion.categoryId = categoriesList[position].id
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
        }
    }

    override fun initAnswersList(answersList: MutableList<Answer>) {
        binding.answersList.apply {
            adapter = OwnAnswerVariantsAdapter(answersList)
            layoutManager = LinearLayoutManager(
                activity?.applicationContext, RecyclerView.VERTICAL, false
            )
        }
    }

    private fun checkPermissionForVideoUploading(){
            requestPermissions(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ), PERMISSIONS_REQUEST_CODE
            )
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
                    startChooseVideoIntent()
                } else {
                    Toast.makeText(activity, R.string.permissions_failed, Toast.LENGTH_LONG).show()
                }
            }
            else -> {
            }
        }
    }

    private fun startChooseVideoIntent() {
        Log.d("startChooseVideoIntent", "startChooseVideoIntent")
        val videoIntent = Intent(INTENT_ACTION_VIDEO_CAMERA)

        startActivityForResult(
            Intent.createChooser(
                videoIntent,
                resources?.getString(R.string.choose_your_avatar)
            ), ProfileController.UPLOAD_AVATAR_TO_PROFILE_REQUEST_CODE
        )
    }

    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    companion object{
        const val PERMISSIONS_REQUEST_CODE = 48345573
    }
}