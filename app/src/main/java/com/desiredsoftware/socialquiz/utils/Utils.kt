package com.desiredsoftware.socialquiz.utils

import com.desiredsoftware.socialquiz.data.model.Profile
import com.desiredsoftware.socialquiz.data.model.QuestionCategory

    fun generateCategories () : ArrayList<QuestionCategory>
    {
        val categoriesList = ArrayList<QuestionCategory>()

        categoriesList.add(QuestionCategory("21","https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
        categoriesList.add(QuestionCategory("23", "https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))
        categoriesList.add(QuestionCategory("43","https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
        categoriesList.add(QuestionCategory("56", "https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))
        categoriesList.add(QuestionCategory("21","https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
        categoriesList.add(QuestionCategory("23", "https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))
        categoriesList.add(QuestionCategory("43","https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
        categoriesList.add(QuestionCategory("56", "https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))
        categoriesList.add(QuestionCategory("21","https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
        categoriesList.add(QuestionCategory("23", "https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))
        categoriesList.add(QuestionCategory("43","https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
        categoriesList.add(QuestionCategory("56", "https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))


        return categoriesList
    }

    fun generateProfile () : Profile
    {
        val properties = ArrayList<Profile.ProfileProperty>()

        properties.add(Profile.ProfileProperty("Username", "kirkadev"))
        properties.add(Profile.ProfileProperty("First name", "Kirill"))
        properties.add(Profile.ProfileProperty("Instagram", "@kirkasukhov"))
        properties.add(Profile.ProfileProperty("About me", "I like music, chess, driving a car, to travel and other things"))
        properties.add(Profile.ProfileProperty("Instagram", "@kirkasukhov"))
        properties.add(Profile.ProfileProperty("About me", "I like music, chess, driving a car, to travel and other things"))
        properties.add(Profile.ProfileProperty("Instagram", "@kirkasukhov"))
        properties.add(Profile.ProfileProperty("About me", "I like music, chess, driving a car, to travel and other things"))

        val profile = Profile("21", "https://pngicon.ru/file/uploads/2_16.png",
        properties)

        return profile
    }

