package com.desiredsoftware.socialquiz.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(context: Context) {

    private val appContext : Context = context

    @Provides
    fun provideContext() : Context{
        return appContext
    }

}