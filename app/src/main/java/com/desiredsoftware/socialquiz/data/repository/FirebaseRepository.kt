package com.desiredsoftware.socialquiz.data.repository

import android.util.Log
import com.desiredsoftware.socialquiz.data.CommonDataSets.Companion.newUserProfileDataSet
import com.desiredsoftware.socialquiz.data.model.category.Category
import com.desiredsoftware.socialquiz.data.model.profile.Profile
import com.desiredsoftware.socialquiz.data.model.profile.Profile.Companion.FIELD_ABOUT
import com.desiredsoftware.socialquiz.data.model.profile.Profile.Companion.FIELD_ACCOUNT_TYPE
import com.desiredsoftware.socialquiz.data.model.profile.Profile.Companion.FIELD_AVATAR_URI
import com.desiredsoftware.socialquiz.data.model.profile.Profile.Companion.FIELD_ID
import com.desiredsoftware.socialquiz.data.model.profile.Profile.Companion.FIELD_INSTAGRAM
import com.desiredsoftware.socialquiz.data.model.profile.Profile.Companion.FIELD_NICK_NAME
import com.desiredsoftware.socialquiz.data.model.profile.Profile.Companion.FIELD_ROLE
import com.desiredsoftware.socialquiz.data.model.profile.Profile.Companion.FIELD_SCORE
import com.desiredsoftware.socialquiz.data.model.profile.Profile.Companion.FIELD_TIK_TOK
import com.desiredsoftware.socialquiz.data.model.question.Question
import com.desiredsoftware.socialquiz.utils.ProfileUtils
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
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

    suspend fun getProfile(uid: String): Profile? {
        firestore.collection(PROFILE_COLLECTION_NAME)
            .whereEqualTo(FIELD_ID, uid)
            .get()
            .await()
            .documents.map { document ->
                try {
                    return Profile(
                        document[FIELD_ID] as String,
                        document[FIELD_AVATAR_URI] as String,
                        document[FIELD_NICK_NAME] as String,
                        document[FIELD_SCORE] as Long,
                        Profile.Companion.ROLES.valueOf(document[FIELD_ROLE] as String),
                        document[FIELD_ABOUT] as String,
                        document[FIELD_INSTAGRAM] as String,
                        document[FIELD_TIK_TOK] as String,
                        Profile.Companion.ACCOUNT_TYPES.valueOf(document[FIELD_ACCOUNT_TYPE] as String)
                    )
                } catch (e: Exception) {
                    Log.e("Profile", "Casting to profile error: ${e.message}")
                    return null
                }
            }
        return null
    }

    fun createProfile(uid: String) {
        val profileCollection = firestore.collection(PROFILE_COLLECTION_NAME)

        val userProfileDataSet = newUserProfileDataSet.toMutableMap()
        userProfileDataSet[FIELD_ID] = uid
        profileCollection.document(uid).set(HashMap(userProfileDataSet))
    }

    fun commitProfile(profileToCommit: Profile){
        val profileCollection = firestore.collection(PROFILE_COLLECTION_NAME)
        val userProfileDataSet = ProfileUtils.getDataSetForProfile(profileToCommit)
        // this action overrides exists document
        profileCollection.document(profileToCommit.id).set(HashMap(userProfileDataSet))
    }

    suspend fun getQuestionsArrayByCategory(
        questionCategoryId: String,
    ): List<Question> {
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

        for (document in eventList) {
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
        val PROFILE_COLLECTION_NAME = "users"
        val CATEGORY_ROOT_COLLECTION = "question_category"
        val QUESTIONS_ROOT_COLLECTION = "questions"
        val FIELD_CATEGORY_NAME = "categoryName"
        val FIELD_CATEGORY_ID = "category_id"
    }

}