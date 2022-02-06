package com.desiredsoftware.socialquiz.data.model.question

class Question(
    var questionId: String = "",
    var categoryId: String = "",
    var answerVariants: List<Answer>? = emptyList(),
    var questionBody: String = "",
    var questionAuthorUid: String = "",
    var questionType: QUESTION_TYPE = QUESTION_TYPE.VIDEO
)
{
    companion object{
        const val FIELD_QUESTION_ID = "QUESTION_ID"
        const val FIELD_CATEGORY_ID = "CATEGORY_ID"
        const val FIELD_ANSWERS_VARIANTS = "ANSWERS_VARIANTS"
        const val FIELD_QUESTION_BODY = "QUESTION_BODY"
        const val FIELD_QUESTION_AUTHOR_ID = "QUESTION_AUTHOR_ID"
        const val FIELD_QUESTION_TYPE = "QUESTION_TYPE"

        enum class QUESTION_TYPE{
            VIDEO, TEXT
        }
    }
}

