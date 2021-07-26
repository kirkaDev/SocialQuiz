package com.desiredsoftware.socialquiz.ui.question

import androidx.lifecycle.ViewModel
import com.desiredsoftware.socialquiz.api.firebase.ApiClientFirebase
import com.desiredsoftware.socialquiz.model.question.Question

class QuestionShowingViewModel () : ViewModel() {

    val apiClient: ApiClientFirebase = ApiClientFirebase()

    lateinit var currentQuestion : Question

}
