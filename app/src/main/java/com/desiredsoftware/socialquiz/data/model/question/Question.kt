package com.desiredsoftware.socialquiz.data.model.question

class Question(
    var questionId: String = "",
    var categoryId: String = "",
    var answerVariants: List<Answer> = emptyList(),
    var questionBody: String = "",
    var questionAuthorUid: String = "",
    var questionType: QUESTION_TYPE = QUESTION_TYPE.VIDEO,
    var rating: Double = default_rating,
    var isApproved: IS_APPROVED = IS_APPROVED.PROPOSED
) {
    companion object {
        const val FIELD_QUESTION_ID = "QUESTION_ID"
        const val FIELD_CATEGORY_ID = "CATEGORY_ID"
        const val FIELD_QUESTION_BODY = "QUESTION_BODY"
        const val FIELD_QUESTION_AUTHOR_ID = "QUESTION_AUTHOR_ID"
        const val FIELD_QUESTION_TYPE = "QUESTION_TYPE"
        const val FIELD_QUESTION_RATING = "QUESTION_RATING"
        const val FIELD_QUESTION_IS_APPROVED = "QUESTION_IS_APPROVED"


        // Sub collection in FB
        const val FIELD_ANSWERS = "ANSWERS"
        const val FIELD_VARIANT = "VARIANT"
        const val FIELD_IS_CORRECT = "IS_CORRECT"

        const val default_rating = 5.0

        enum class QUESTION_TYPE {
            VIDEO, TEXT
        }

        enum class IS_APPROVED {
            PROPOSED, APPROVED, DENIED
        }
    }
}

