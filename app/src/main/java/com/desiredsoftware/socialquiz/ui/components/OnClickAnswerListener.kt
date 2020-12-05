package com.desiredsoftware.socialquiz.ui.components

import com.desiredsoftware.socialquiz.data.model.question.Question

interface OnClickAnswerListener {
    fun onClicked(answerVariant: Question.Answer)
}