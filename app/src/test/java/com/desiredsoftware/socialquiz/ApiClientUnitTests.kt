package com.desiredsoftware.socialquiz

import com.desiredsoftware.socialquiz.api.`in`.ApiClientFirebase
import com.desiredsoftware.socialquiz.model.question.GetQuestionsCallback
import org.junit.Assert
import org.junit.Test


class ApiClientUnitTests {

    @Test
    fun getQuestion() {

        val category: String = "Music"
        val client: ApiClientFirebase = ApiClientFirebase()
        val questionsCallback: GetQuestionsCallback

        lateinit var questions: ArrayList<Any>


/*
        client.getQuestion(category, object : GetQuestionsCallback {
            override fun onCallback(questionsArray: ArrayList<Any>) {
                questions = questionsArray
            }
        })
*/

        Assert.assertEquals(true, false)
    }

}