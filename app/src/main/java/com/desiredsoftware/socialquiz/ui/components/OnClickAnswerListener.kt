package com.desiredsoftware.socialquiz.ui.components

import com.desiredsoftware.socialquiz.model.question.Question

interface OnClickAnswerListener {
    fun onClicked(answerVariant: Question.Answer)
}