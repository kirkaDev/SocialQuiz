package com.desiredsoftware.socialquiz

import com.desiredsoftware.socialquiz.data.model.question.Question
import org.junit.Test

import org.junit.Assert.*


class QuestionUnitTest {
    @Test
    fun hasSeveralCorrectAnswers() {

        val answerVariants : ArrayList<Question.Answer> = ArrayList<Question.Answer>()

        answerVariants.add(Question.Answer(true, "Cat"))
        answerVariants.add(Question.Answer(false, "Shake"))
        answerVariants.add(Question.Answer(true, "Dog"))

        val question = Question("1",
                "text",
                "Who has 4 paws?",
                answerVariants
        )

        val hasSeveralCorrectAnswers : Boolean = question.hasSeveralCorrectAnswers()

        assertEquals(true, hasSeveralCorrectAnswers)
    }

    @Test
    fun hasOneCorrectAnswer() {

        val answerVariants : ArrayList<Question.Answer> = ArrayList<Question.Answer>()

        answerVariants.add(Question.Answer(true, "Cat"))
        answerVariants.add(Question.Answer(false, "Shake"))
        answerVariants.add(Question.Answer(false, "Shark"))

        val question = Question("1",
                "text",
                "Who has 4 paws?",
                answerVariants
        )

        val hasSeveralCorrectAnswers : Boolean = question.hasSeveralCorrectAnswers()

        assertEquals(true, !hasSeveralCorrectAnswers)
    }

    @Test
    fun hasNotAnyCorrectAnswer() {

        val answerVariants : ArrayList<Question.Answer> = ArrayList<Question.Answer>()

        answerVariants.add(Question.Answer(false, "Octocat"))
        answerVariants.add(Question.Answer(false, "Shake"))
        answerVariants.add(Question.Answer(false, "Shark"))

        val question = Question("1",
                "text",
                "Who has 4 paws?",
                answerVariants
        )

        val hasSeveralCorrectAnswers : Boolean = question.hasSeveralCorrectAnswers()

        assertEquals(true, !hasSeveralCorrectAnswers)
    }
}