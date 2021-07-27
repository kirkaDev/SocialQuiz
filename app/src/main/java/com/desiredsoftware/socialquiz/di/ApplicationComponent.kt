package com.desiredsoftware.socialquiz.di

import com.desiredsoftware.socialquiz.ui.categories.CategoriesController
import dagger.Component

@Component (modules = [AppModule::class, FirebaseModule::class])
interface ApplicationComponent {
    fun inject(categoriesController: CategoriesController)
}