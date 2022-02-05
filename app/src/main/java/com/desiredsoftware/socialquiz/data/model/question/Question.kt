package com.desiredsoftware.socialquiz.data.model.question

class Question(
    var mAnswers: List<Answer>? = emptyList(),
    var mCategoryName: String = "",
    var mCategory_id: String = "",
    var mLanguage: String = "",
    var mQuestionBody: String = "",
    var mQuestionOwner: String = "",
    var mQuestionType: String = ""
        )