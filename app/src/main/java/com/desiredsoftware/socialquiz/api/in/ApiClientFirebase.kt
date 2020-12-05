package com.desiredsoftware.socialquiz.api.`in`

import android.util.Log
import com.desiredsoftware.socialquiz.api.`in`.category.GetCategoriesCallback
import com.desiredsoftware.socialquiz.data.model.question.GetQuestionCallback
import com.desiredsoftware.socialquiz.data.model.question.Question
import com.desiredsoftware.socialquiz.data.model.question.QuestionCategory
import com.google.firebase.firestore.FirebaseFirestore


class ApiClientFirebase {

    val CategoryCollectionName : String = "question_category"
    val QuestionsCollectionName : String = "questions"

    var db = FirebaseFirestore.getInstance()

    fun getCategories(callback: GetCategoriesCallback)
    {
        db.collection(CategoryCollectionName)
            .get()
            .addOnCompleteListener { task ->

                var questionCategories :  ArrayList<QuestionCategory> = ArrayList()

                if (task.isSuccessful) {

                    for (document in task.result!!) {
                        Log.d("Firebase read", document.id + " => " + document.data)
                        questionCategories.add(QuestionCategory(
                                document.data["category_id"] as String,
                                document.data["isPremium"] as String,
                                document.data["imageResource"] as String,
                                document.data["categoryName"] as String
                        ))
                    }

                } else {
                    Log.w("Firebase read", "Error getting documents.", task.exception)
                }
                callback.onCallback(questionCategories)
            }
    }

    fun getQuestion(questionCategory: String, callback: GetQuestionCallback)
    {
        var answersArray :  ArrayList<Question> = ArrayList()
        var answersSomeObjects :  ArrayList<Any> = ArrayList()
        val collectionName = "questions"


        db.collection(collectionName)
                .whereEqualTo("categoryName", questionCategory)
                .get()
                .addOnCompleteListener { task ->
                    var questionsArray :  ArrayList<Any> = ArrayList()

                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                          Log.d("Firebase read", document.id + " => " + document.data)
                            questionsArray.add(document.data)
                        }

                    } else {
                        Log.w("Firebase read", "Error getting documents.", task.exception)
                    }
                    callback.onCallback(questionsArray)
                }
    }


}