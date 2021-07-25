package com.desiredsoftware.socialquiz.di

import com.desiredsoftware.socialquiz.ui.categories.CategoriesFragment
import dagger.Component

@Component (modules = [AppModule::class, FirebaseModule::class])
interface ApplicationComponent {
    fun inject(categoriesFragment: CategoriesFragment)
}