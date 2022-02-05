package com.desiredsoftware.socialquiz.ui.components

import com.desiredsoftware.socialquiz.data.model.question.Answer

interface OnClickAnswerListener {
    fun onClicked(answer: Answer)
}