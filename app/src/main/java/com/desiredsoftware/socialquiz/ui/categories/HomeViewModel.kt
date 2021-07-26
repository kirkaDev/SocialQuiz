package com.desiredsoftware.socialquiz.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desiredsoftware.socialquiz.api.firebase.ApiClientFirebase
import com.desiredsoftware.socialquiz.model.question.GetQuestionsCallback
import com.desiredsoftware.socialquiz.model.question.Question
import com.desiredsoftware.socialquiz.utils.generateQuestion

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Select your category"
    }
    val text: LiveData<String> = _text

    val apiClient: ApiClientFirebase = ApiClientFirebase()

    lateinit var localQuestionArray : ArrayList<Any>

    fun getQuestionsInCategory(questionCategory : String, callback : GetQuestionsCallback) : Question
    {
        apiClient.getQuestionsArrayByCategory(questionCategory, callback)

        return generateQuestion()
    }

}