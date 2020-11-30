package com.desiredsoftware.socialquiz.data.model.question

import com.desiredsoftware.socialquiz.data.model.Profile

class Question (val questionId: String,
                val questionType: String,
                val questionBody: String,
                val questionOwnerId: Profile,
                val answerVariants : ArrayList<Answer>  ) {

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

    class Answer(val isCorrect : Boolean, val answer : String){

    }


}