package com.desiredsoftware.socialquiz.di

import com.desiredsoftware.socialquiz.ui.auth.AuthController
import com.desiredsoftware.socialquiz.ui.categories.CategoriesController
import com.desiredsoftware.socialquiz.ui.profile.ProfileController
import com.desiredsoftware.socialquiz.ui.question.QuestionResultController
import com.desiredsoftware.socialquiz.ui.question.QuestionShowingController
import com.desiredsoftware.socialquiz.ui.splash.SplashActivity
import dagger.Component

@Component(modules = [AppModule::class, FirebaseModule::class])
interface ApplicationComponent {
    fun inject(categoriesController: CategoriesController)
    fun inject(questionShowingController: QuestionShowingController)
    fun inject(questionResultController: QuestionResultController)
    fun inject(splashActivity: SplashActivity)
    fun inject(authController: AuthController)
    fun inject(profileController: ProfileController)
}