package com.desiredsoftware.socialquiz.api.`in`.category

import com.desiredsoftware.socialquiz.data.model.question.QuestionCategory

interface GetCategoriesCallback {
    fun onCallback(categories : ArrayList<QuestionCategory>)
}