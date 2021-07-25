package com.desiredsoftware.socialquiz.data.repository

import com.desiredsoftware.socialquiz.api.`in`.ApiClientFirebase
import com.desiredsoftware.socialquiz.model.question.QuestionCategory

class FirebaseRepository() {

    val apiClient: ApiClientFirebase = ApiClientFirebase()
    var categoriesList = ArrayList<QuestionCategory>()


    fun getQuestionCategories() : List<QuestionCategory>{
        return emptyList()
    }
}