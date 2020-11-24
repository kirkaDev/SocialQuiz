package com.desiredsoftware.socialquiz.utils

import com.desiredsoftware.socialquiz.data.model.Profile
import com.desiredsoftware.socialquiz.data.model.api.`in`.category.Category

    fun generateCategories () : ArrayList<Category>
    {
        val categoriesList = ArrayList<Category>()

        categoriesList.add(Category("https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
        categoriesList.add(Category("https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))
        categoriesList.add(Category("https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
        categoriesList.add(Category("https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))
        categoriesList.add(Category("https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
        categoriesList.add(Category("https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))
        categoriesList.add(Category("https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
        categoriesList.add(Category("https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))
        categoriesList.add(Category("https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
        categoriesList.add(Category("https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))

        return categoriesList
    }

    fun generateProfile () : Profile
    {
        val profile = Profile("21", "Kirill Sukhov","https://pngicon.ru/file/uploads/2_16.png",
        "1", "@kirkasukhov", "I'm cool")

        return profile
    }