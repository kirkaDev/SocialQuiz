package com.desiredsoftware.socialquiz.ui.question

import androidx.lifecycle.ViewModel
import com.desiredsoftware.socialquiz.api.`in`.ApiClientFirebase
import com.desiredsoftware.socialquiz.data.model.question.GetQuestionCallback
import com.desiredsoftware.socialquiz.data.model.question.Question
import com.desiredsoftware.socialquiz.utils.generateQuestion

class QuestionShowingViewModel () : ViewModel() {

    val apiClient: ApiClientFirebase = ApiClientFirebase()

    fun getNextQuestion(questionCategory : String, callback : GetQuestionCallback) : Question
    {
        apiClient.getQuestion(questionCategory, callback)

        return generateQuestion()
    }

}
