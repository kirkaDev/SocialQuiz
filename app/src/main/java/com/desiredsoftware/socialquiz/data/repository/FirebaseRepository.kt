package com.desiredsoftware.socialquiz.data.repository

import android.util.Log
import com.desiredsoftware.socialquiz.model.category.Category
import com.desiredsoftware.socialquiz.model.question.GetQuestionsCallback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.tasks.await


class FirebaseRepository() {

    companion object {
        val CategoryCollectionName = "question_category"
        val QuestionsCollectionName = "questions"
    }

    var firestore = FirebaseFirestore.getInstance()

    init {
        val settings = FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build()
        firestore.firestoreSettings = settings
    }


    suspend fun getCategories() = firestore.collection(CategoryCollectionName)
            .get()
            .await().documents.map {
            Category(
                it["category_id"] as String,
                it["isPremium"] as String,
                it["imageResource"] as String,
                it["categoryName"] as String
            )
        }


    fun getQuestionsArrayByCategory(questionCategory: String, callback: GetQuestionsCallback) {
        // TODO: Make read questions from cached version for next cases
        val collectionName = "questions"

        firestore.collection(collectionName)
                .whereEqualTo("categoryName", questionCategory)
                .get()
                .addOnCompleteListener { task ->
                    var questionsArray: ArrayList<Any> = ArrayList()

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