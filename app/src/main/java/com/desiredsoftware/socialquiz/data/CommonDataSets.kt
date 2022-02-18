package com.desiredsoftware.socialquiz.data

import com.desiredsoftware.socialquiz.data.model.profile.Profile
import com.desiredsoftware.socialquiz.data.model.question.Question
import com.desiredsoftware.socialquiz.data.model.question.Question.Companion.FIELD_IS_CORRECT
import com.desiredsoftware.socialquiz.data.model.question.Question.Companion.FIELD_VARIANT

class CommonDataSets {
    companion object{
        val newUserProfileDataSet = hashMapOf(
            Profile.FIELD_AVATAR_URI to "",
            Profile.FIELD_NICK_NAME to "New user",
            Profile.FIELD_SCORE to 0L,
            Profile.FIELD_ROLE to Profile.Companion.ROLES.NEW.toString(),
            Profile.FIELD_ABOUT to String(),
            Profile.FIELD_INSTAGRAM to String(),
            Profile.FIELD_TIK_TOK to String(),
            Profile.FIELD_ACCOUNT_TYPE to Profile.Companion.ACCOUNT_TYPES.FREE.toString(),
        )

        fun getQuestionDataSet(question: Question): HashMap<Any, Any>{
            val answersArrayList = ArrayList<HashMap<String, Any>>()
            question.answerVariants.forEach { answer ->
                answersArrayList.add(
                    hashMapOf(
                        FIELD_VARIANT to answer.answer,
                        FIELD_IS_CORRECT to answer.isCorrect
                    )
                )
            }

            return hashMapOf(
                Question.FIELD_QUESTION_ID to question.questionId,
                Question.FIELD_CATEGORY_ID to question.categoryId,
                Question.FIELD_ANSWERS to answersArrayList,
                Question.FIELD_QUESTION_BODY to question.questionBody,
                Question.FIELD_QUESTION_AUTHOR_ID to question.questionAuthorUid,
                Question.FIELD_QUESTION_TYPE to question.questionType.toString(),
                Question.FIELD_QUESTION_RATING to question.rating.toString(),
                Question.FIELD_QUESTION_IS_APPROVED to question.isApproved.toString()
            )
        }
    }
}