package com.desiredsoftware.socialquiz

import com.desiredsoftware.socialquiz.data.model.question.Question
import org.junit.Assert.assertEquals
import org.junit.Test


class QuestionUnitTests {
    @Test
    fun hasSeveralCorrectAnswers() {

        val answerVariants: ArrayList<Question.Answer> = ArrayList<Question.Answer>()

        answerVariants.add(Question.Answer( "Cat", true))
        answerVariants.add(Question.Answer("Shake", false))
        answerVariants.add(Question.Answer("Dog", true))

        val question = Question(
            "1",
            "text",
            "Who has 4 paws?",
                "kirkadev",
            answerVariants
        )

        val hasSeveralCorrectAnswers: Boolean = question.hasSeveralCorrectAnswers()

        assertEquals(true, hasSeveralCorrectAnswers)
    }

    @Test
    fun hasOneCorrectAnswer() {

        val answerVariants: ArrayList<Question.Answer> = ArrayList<Question.Answer>()

        answerVariants.add(Question.Answer( "Cat", true))
        answerVariants.add(Question.Answer( "Shake", false))
        answerVariants.add(Question.Answer("Shark", false, ))

        val question = Question(
            "1",
            "text",
            "Who has 4 paws?",
                "kirkadev",
            answerVariants
        )

        val hasSeveralCorrectAnswers: Boolean = question.hasSeveralCorrectAnswers()

        assertEquals(true, !hasSeveralCorrectAnswers)
    }

    @Test
    fun hasNotAnyCorrectAnswer() {

        val answerVariants: ArrayList<Question.Answer> = ArrayList<Question.Answer>()

        answerVariants.add(Question.Answer("Octocat", false))
        answerVariants.add(Question.Answer("Shake", false, ))
        answerVariants.add(Question.Answer( "Shark", false,))

        val question = Question(
            "1",
            "text",
            "Who has 4 paws?",
            "kirkadev",
            answerVariants
        )

        val hasSeveralCorrectAnswers: Boolean = question.hasSeveralCorrectAnswers()

        assertEquals(true, !hasSeveralCorrectAnswers)
    }
}