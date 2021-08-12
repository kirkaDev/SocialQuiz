package com.desiredsoftware.socialquiz.model.question

class Question(
    var mAnswers: List<Answer>? = emptyList(),
    var mCategoryName: String = "",
    var mCategory_id: String = "",
    var mLanguage: String = "",
    var mQuestionBody: String = "",
    var mQuestionOwner: String = "",
    var mQuestionType: String = ""
        ) {

/*    fun hasSeveralCorrectAnswers() : Boolean
    {
        var hasCurrentAnswer = false

        for (answer in mAnswerVariants) {
            if (answer is Answer && answer.isCorrect) {
                if (hasCurrentAnswer)
                {
                    return true
                }
                hasCurrentAnswer = true
            }
        }

        // returns false if here has 0 or 1 correct answer
        return false
    }*/
}