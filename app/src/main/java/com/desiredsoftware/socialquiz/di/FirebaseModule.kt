package com.desiredsoftware.socialquiz.di

import com.desiredsoftware.socialquiz.data.repository.FirebaseRepository
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {

    @Provides
    fun provideFirebaseRepository() : FirebaseRepository{
        return FirebaseRepository()
    }
}