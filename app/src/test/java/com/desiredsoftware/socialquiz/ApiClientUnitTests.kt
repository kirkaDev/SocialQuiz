package com.desiredsoftware.socialquiz

import com.desiredsoftware.socialquiz.api.`in`.ApiClientFirebase
import com.desiredsoftware.socialquiz.data.model.question.GetQuestionCallback
import org.junit.Assert
import org.junit.Test


class ApiClientUnitTests {

    @Test
    fun getQuestion() {

        val category: String = "Music"
        val client: ApiClientFirebase = ApiClientFirebase()
        val questionCallback: GetQuestionCallback

        lateinit var questions: ArrayList<Any>


        client.getQuestion(category, object : GetQuestionCallback {
            override fun onCallback(questionsArray: ArrayList<Any>) {
                questions = questionsArray
            }
        })

        Assert.assertEquals(true, true)
    }

}