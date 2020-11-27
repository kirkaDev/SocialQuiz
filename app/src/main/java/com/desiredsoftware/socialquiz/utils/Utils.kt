package com.desiredsoftware.socialquiz.utils

import com.desiredsoftware.socialquiz.data.model.Profile
import com.desiredsoftware.socialquiz.data.model.question.Question
import com.desiredsoftware.socialquiz.data.model.question.QuestionCategory

    fun generateCategories () : ArrayList<QuestionCategory>
    {
        val categoriesList = ArrayList<QuestionCategory>()

        categoriesList.add(QuestionCategory("21","https://img.icons8.com/color/2x/owl.png", "Animals"))
        categoriesList.add(QuestionCategory("23", "https://img.icons8.com/color/2x/heart-with-pulse.png", "Health"))
        categoriesList.add(QuestionCategory("43","https://img.icons8.com/doodle/2x/controller--v1.png", "Video Games"))
        categoriesList.add(QuestionCategory("56", "https://img.icons8.com/color/2x/boy-stroller.png", "Children"))
        categoriesList.add(QuestionCategory("21","https://img.icons8.com/color/2x/cheburashka.png", "Cartoons"))
        categoriesList.add(QuestionCategory("23", "https://img.icons8.com/color/2x/project.png", "Business"))
        categoriesList.add(QuestionCategory("21","https://img.icons8.com/cute-clipart/2x/outdoor-swimming-pool.png", "Rest"))
        categoriesList.add(QuestionCategory("21","https://img.icons8.com/color/2x/owl.png", "Animals"))
        categoriesList.add(QuestionCategory("23", "https://img.icons8.com/color/2x/heart-with-pulse.png", "Health"))
        categoriesList.add(QuestionCategory("43","https://img.icons8.com/doodle/2x/controller--v1.png", "Video Games"))
        categoriesList.add(QuestionCategory("56", "https://img.icons8.com/color/2x/boy-stroller.png", "Children"))
        categoriesList.add(QuestionCategory("21","https://img.icons8.com/color/2x/cheburashka.png", "Cartoons"))

        return categoriesList
    }

    fun generateProfile () : Profile
    {
        val properties = ArrayList<Profile.ProfileProperty>()

        properties.add(Profile.ProfileProperty("Username", "kirkadev"))
        properties.add(Profile.ProfileProperty("First name", "Kirill"))
        properties.add(Profile.ProfileProperty("Instagram", "@kirkasukhov"))
        properties.add(Profile.ProfileProperty("About me", "I like music, chess, driving a car, to travel and other things"))
        properties.add(Profile.ProfileProperty("Telegram", "@kirkadev"))
        properties.add(Profile.ProfileProperty("About me", "I like music, chess, driving a car, to travel and other things"))
        properties.add(Profile.ProfileProperty("Instagram", "@kirkasukhov"))
        properties.add(Profile.ProfileProperty("About me", "I like music, chess, driving a car, to travel and other things"))

        val profile = Profile("21", "https://pngicon.ru/file/uploads/2_16.png",

        //val profile = Profile("21", "https://bipbap.ru/wp-content/uploads/2017/05/VOLKI-krasivye-i-ochen-umnye-zhivotnye.jpg",

        properties)

        return profile
    }

    fun generateQuestion() : Question
    {
        val answerVariants : ArrayList<Question.Answer> = ArrayList<Question.Answer>()

        answerVariants.add(Question.Answer(true, "Cat"))
        answerVariants.add(Question.Answer(false, "Shake"))
        answerVariants.add(Question.Answer(true, "Dog"))

        return Question("1",
                "text",
                "Who has 4 paws?",
                answerVariants
        )
    }

