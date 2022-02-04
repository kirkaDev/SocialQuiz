package com.desiredsoftware.socialquiz.di

import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {

    @Provides
    fun provideFirebaseRepository(): FirebaseRepository {
        return FirebaseRepository(
            provideFirestoreInstance()
        )
    }

    @Provides
    fun provideFirestoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideFirestoreStorage(): StorageReference {
        return FirebaseStorage.getInstance().reference
    }

    @Provides
    fun provideUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }
}