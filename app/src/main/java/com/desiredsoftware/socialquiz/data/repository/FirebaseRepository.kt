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
import com.desiredsoftware.socialquiz.data.model.question.Answer
import com.desiredsoftware.socialquiz.data.model.question.Question
import com.desiredsoftware.socialquiz.data.model.question.Question.Companion.FIELD_ANSWERS
import com.desiredsoftware.socialquiz.data.model.question.Question.Companion.FIELD_CATEGORY_ID
import com.desiredsoftware.socialquiz.data.model.question.Question.Companion.FIELD_IS_CORRECT
import com.desiredsoftware.socialquiz.data.model.question.Question.Companion.FIELD_QUESTION_AUTHOR_ID
import com.desiredsoftware.socialquiz.data.model.question.Question.Companion.FIELD_QUESTION_BODY
import com.desiredsoftware.socialquiz.data.model.question.Question.Companion.FIELD_QUESTION_ID
import com.desiredsoftware.socialquiz.data.model.question.Question.Companion.FIELD_QUESTION_TYPE
import com.desiredsoftware.socialquiz.data.model.question.Question.Companion.FIELD_VARIANT
import com.desiredsoftware.socialquiz.utils.ProfileUtils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
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

    suspend fun getCategories(): List<Category> {
        val categoriesList = mutableListOf<Category>()
        firestore.collection(CATEGORY_ROOT_COLLECTION)
            .get()
            .await().documents.forEach {
                try {
                    categoriesList.add(
                        Category(
                            it["category_id"] as String,
                            it["isPremium"] as Boolean,
                            it["imageResource"] as String,
                            it["categoryName"] as String
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("Casting from DB error", "Categories casting error, document=${it.data}")
                }
            }

        return categoriesList
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

    fun commitProfile(profileToCommit: Profile) {
        val profileCollection = firestore.collection(PROFILE_COLLECTION_NAME)
        val userProfileDataSet = ProfileUtils.getDataSetForProfile(profileToCommit)
        // this action overrides exists document
        profileCollection.document(profileToCommit.id).set(HashMap(userProfileDataSet))
    }

    suspend fun getQuestionsOfCategory(
        questionCategoryId: String,
    ): List<Question> {
        Log.d("question", "Repository: getQuestionsOfCategory called")
        val questionList = mutableListOf<Question>()
        firestore.collection(QUESTIONS_ROOT_COLLECTION)
            //.whereEqualTo(FIELD_CATEGORY_ID, questionCategoryId)
            .get()
            .await()
            .documents.map { document ->
                try {
                    val cleanAnswers: List<Answer> = (document.get(FIELD_ANSWERS)
                            as ArrayList<HashMap<String, Any>>).map {
                        Answer(
                            it[FIELD_VARIANT] as String,
                            it[FIELD_IS_CORRECT] as Boolean
                        )
                    }

                    questionList.add(
                        Question(
                            document[FIELD_QUESTION_ID] as String,
                            document[FIELD_CATEGORY_ID] as String,
                            cleanAnswers,
                            document[FIELD_QUESTION_BODY] as String,
                            document[FIELD_QUESTION_AUTHOR_ID] as String,
                            Question.Companion.QUESTION_TYPE.valueOf(document[FIELD_QUESTION_TYPE] as String)
                        )
                    )
                } catch (e: Exception) {
                    Log.e("Profile", "Casting to Question error: ${e.message}")
                }
            }

        return questionList
    }

    suspend fun addPoints(points: Long): Int {
        val profile: Profile?

        Firebase.auth.currentUser?.let {
            profile = getProfile(it.uid)
            profile?.let { profile ->
                profile.score += points
                commitProfile(profile)
                return OPERATION_RESULT_SUCCESS
            }
        } ?: kotlin.run {
            return OPERATION_RESULT_FAILED
        }
    }

    companion object {
        const val PROFILE_COLLECTION_NAME = "users"
        const val CATEGORY_ROOT_COLLECTION = "question_category"
        const val QUESTIONS_ROOT_COLLECTION = "questions"
        const val OPERATION_RESULT_SUCCESS = 1
        const val OPERATION_RESULT_FAILED = -1
    }

}