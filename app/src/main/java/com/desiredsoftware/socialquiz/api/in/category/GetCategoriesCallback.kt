package com.desiredsoftware.socialquiz.api.`in`.category

import com.desiredsoftware.socialquiz.model.question.QuestionCategory

interface GetCategoriesCallback {
    fun onCallback(categories : ArrayList<QuestionCategory>)
}