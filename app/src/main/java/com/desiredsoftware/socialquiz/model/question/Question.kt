package com.desiredsoftware.socialquiz.model.question
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
class Question (
    var mAnswerVariants : @RawValue List<Answer> = emptyList<Answer>(),
    var mCategoryName: String = "",
    var mCategory_id: String = "",
    var mLanguage: String = "",
    var mQuestionBody: String = "",
    var mQuestionOwner: String = "",
    var mQuestionType: String = ""
        ) : Parcelable {

    fun hasSeveralCorrectAnswers() : Boolean
    {
        var hasCurrentAnswer = false

        for (answer in mAnswerVariants) {
            if (answer.isCorrect) {
                if (hasCurrentAnswer)
                {
                    return true
                }
                hasCurrentAnswer = true
            }
        }

        // returns false if here has 0 or 1 correct answer
        return false
    }
}