package com.desiredsoftware.socialquiz.model.question

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class Question (val categoryName: String,
                val questionType: String,
                val questionBody: String,
                val questionOwner: String,
                val answerVariants : @RawValue ArrayList<Answer>  ) : Parcelable {

    fun hasSeveralCorrectAnswers() : Boolean
    {
        var hasCurrentAnswer = false

        for (answer in answerVariants) {
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

    class Answer(val answer : String, val isCorrect : Boolean){

    }


}