package com.desiredsoftware.socialquiz.data.repository

import com.desiredsoftware.socialquiz.model.category.Category
import com.desiredsoftware.socialquiz.model.question.Question
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.tasks.await


class FirebaseRepository() {

    var firestore = FirebaseFirestore.getInstance()

    init {
        val settings = FirebaseFirestoreSettings.Builder()
            .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
            .build()
        firestore.firestoreSettings = settings
    }


    suspend fun getCategories() = firestore.collection(CATEGORY_ROOT_COLLECTION)
        .get()
        .await().documents.map {
            Category(
                it["category_id"] as String,
                it["isPremium"] as String,
                it["imageResource"] as String,
                it["categoryName"] as String
            )
        }


    suspend fun getQuestionsArrayByCategory(
        questionCategoryId: String,
    ) : List<Question> {
        var questionList = mutableListOf<Question>()

        val eventList = firestore.collection(QUESTIONS_ROOT_COLLECTION)
            .whereEqualTo(FIELD_CATEGORY_ID, questionCategoryId)
            .get()
            .await()/* {
                Question(
                    mAnswerVariants = it["answerVariants"] as List<Any?>,
                    mCategoryName = it["categoryName"] as String,
                    mCategory_id = it["category_id"] as String,
                    mLanguage = it["language"] as String,
                    mQuestionBody = it["questionBody"] as String,
                    mQuestionOwner = it["questionOwner"] as String,
                    mQuestionType = it["questionType"] as String
                )
                Log.d("Firebase", "Questions is read: ${it.toString()}")
            }*/

        for (document in eventList){
            //val a: MutableMap<"answerVariants", Boolean> = document.data
            val mCategoryName = document.getString("categoryName")
            val mCategory_id = document.getString("category_id")
            val mLanguage = document.getString("language")
            val mQuestionBody = document.getString("questionBody")
            val mQuestionOwner = document.getString("questionOwner")
            val mQuestionType = document.getString("questionType")
        }

        return emptyList()
    }

    companion object {
        val CATEGORY_ROOT_COLLECTION = "question_category"
        val QUESTIONS_ROOT_COLLECTION = "questions"
        val FIELD_CATEGORY_NAME = "categoryName"
        val FIELD_CATEGORY_ID = "category_id"
    }

}