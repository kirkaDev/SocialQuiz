package com.desiredsoftware.socialquiz.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class SharedPreferencesModule {

    @Named(SOLVED_QUESTIONS_PREFS)
    @Provides
    @Singleton
    fun provideSolvedQuestions(context: Context): SharedPreferences =
        context.getSharedPreferences(SOLVED_QUESTIONS_PREFS, Context.MODE_PRIVATE)

    companion object {
        const val SOLVED_QUESTIONS_PREFS = "SOLVED_QUESTIONS_PREFS"
    }
}