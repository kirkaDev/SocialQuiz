package com.desiredsoftware.socialquiz.api.`in`

import android.util.Log
import com.desiredsoftware.socialquiz.api.`in`.category.GetCategoriesCallback
import com.desiredsoftware.socialquiz.data.model.question.GetQuestionsCallback
import com.desiredsoftware.socialquiz.data.model.question.QuestionCategory
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings


class ApiClientFirebase {

    val CategoryCollectionName : String = "question_category"
    val QuestionsCollectionName : String = "questions"


    var db = FirebaseFirestore.getInstance()

    init {
        val settings = FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build()
            db.firestoreSettings = settings
        }


    fun getCategories(callback: GetCategoriesCallback)
    {
        // TODO: Make read categories from cached version for next cases
        //if (usingCachedDataMode) db.disableNetwork() else db.enableNetwork()
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

    fun getQuestionsArrayByCategory(questionCategory: String, callback: GetQuestionsCallback)
    {
        // TODO: Make read questions from cached version for next cases
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