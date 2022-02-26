package com.desiredsoftware.socialquiz.di

import com.desiredsoftware.socialquiz.ui.categories.CategoriesController
import com.desiredsoftware.socialquiz.ui.profile.ProfileController
import com.desiredsoftware.socialquiz.ui.question.AddOwnQuestionController
import com.desiredsoftware.socialquiz.ui.question.QuestionResultController
import com.desiredsoftware.socialquiz.ui.question.QuestionShowingController
import com.desiredsoftware.socialquiz.ui.splash.SplashController
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules =
    [FirebaseModule::class,
    SharedPreferencesModule::class])
interface ApplicationComponent {
    fun inject(categoriesController: CategoriesController)
    fun inject(questionShowingController: QuestionShowingController)
    fun inject(questionResultController: QuestionResultController)
    fun inject(splashController: SplashController)
    fun inject(profileController: ProfileController)
    fun inject(addOwnQuestionController: AddOwnQuestionController)
}