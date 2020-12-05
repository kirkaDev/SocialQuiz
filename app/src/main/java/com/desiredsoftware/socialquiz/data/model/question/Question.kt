package com.desiredsoftware.socialquiz.data.model.question

class Question (val categoryName: String,
                val questionType: String,
                val questionBody: String,
                val questionOwner: String,
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

    class Answer(val answer : String, val isCorrect : Boolean){

    }


}