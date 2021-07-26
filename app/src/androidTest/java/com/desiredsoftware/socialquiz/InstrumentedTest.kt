package com.desiredsoftware.socialquiz

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.desiredsoftware.socialquiz.api.firebase.ApiClientFirebase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.desiredsoftware.socialquiz", appContext.packageName)
    }

    @Test
    fun getQuestion() {

        val category: String = "Music"
        val client: ApiClientFirebase = ApiClientFirebase()

        lateinit var questions: ArrayList<Any>

/*        client.getQuestion(category, object : GetQuestionsCallback {
            override fun onCallback(questionsArray: ArrayList<Any>) {
                questions = questionsArray

                Assert.assertEquals(true, false)
            }
        })*/
    }

}