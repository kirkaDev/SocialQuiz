package com.desiredsoftware.socialquiz.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desiredsoftware.socialquiz.api.`in`.ApiClientFirebase
import com.desiredsoftware.socialquiz.data.model.question.GetQuestionCallback
import com.desiredsoftware.socialquiz.data.model.question.Question
import com.desiredsoftware.socialquiz.utils.generateQuestion

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Select your category"
    }
    val text: LiveData<String> = _text

    val apiClient: ApiClientFirebase = ApiClientFirebase()

    fun getNextQuestion(questionCategory : String, callback : GetQuestionCallback) : Question
    {
        apiClient.getQuestion(questionCategory, callback)

        return generateQuestion()
    }

}