package com.desiredsoftware.socialquiz.utils

import com.desiredsoftware.socialquiz.model.category.Category
import com.desiredsoftware.socialquiz.model.profile.Profile
import com.desiredsoftware.socialquiz.model.question.Question

fun generateCategories () : ArrayList<Category>
    {
        val categoriesList = ArrayList<Category>()

        categoriesList.add(Category("223", "false", "https://img.icons8.com/color/2x/owl.png", "Animals"))
        categoriesList.add(Category("23","false", "https://img.icons8.com/color/2x/heart-with-pulse.png", "Health"))
        categoriesList.add(Category("43","false","https://img.icons8.com/doodle/2x/controller--v1.png", "Video Games"))
        categoriesList.add(Category("56", "false","https://img.icons8.com/color/2x/boy-stroller.png", "Children"))
        categoriesList.add(Category("21","false","https://img.icons8.com/color/2x/cheburashka.png", "Cartoons"))
        categoriesList.add(Category("23", "true","https://img.icons8.com/color/2x/project.png", "Business"))
        categoriesList.add(Category("21","false","https://img.icons8.com/cute-clipart/2x/outdoor-swimming-pool.png", "Travel"))
        categoriesList.add(Category("223","false","https://img.icons8.com/color/2x/owl.png", "Animals"))
        categoriesList.add(Category("23", "true","https://img.icons8.com/color/2x/heart-with-pulse.png", "Health"))

        return categoriesList
    }

    fun generateProfile () : Profile
    {
        val properties = ArrayList<Profile.ProfileProperty>()

        properties.add(Profile.ProfileProperty("Username", "kirkadev"))
        properties.add(Profile.ProfileProperty("First name", "Kirill"))
        properties.add(Profile.ProfileProperty("Role", "Admin"))
        properties.add(Profile.ProfileProperty("Github", "https://github.com/kirkaDev"))
        properties.add(Profile.ProfileProperty("Instagram", "@kirkasukhov"))
        properties.add(Profile.ProfileProperty("Telegram", "@kirkadev"))
        properties.add(Profile.ProfileProperty("About me", "I like music, chess, driving a car, to travel and other things"))


        val profile = Profile("21", "https://pngicon.ru/file/uploads/2_16.png", true, properties)

        return profile
    }

    fun generateQuestion() : Question
    {
        val answerVariants : ArrayList<Question.Answer> = ArrayList<Question.Answer>()

        answerVariants.add(Question.Answer("Cat", true))
        answerVariants.add(Question.Answer("Shake", false))
        answerVariants.add(Question.Answer("Dog", true))
        answerVariants.add(Question.Answer("Dolphin", false))
        answerVariants.add(Question.Answer("Zebra",true))

        return Question(
                "Music",
                "text",
                "Who has 4 paws?",
                "kirkadev",
                answerVariants
        )
    }

    fun generateMediaURI() : String
    {
        return "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4"
    }

    fun convertToQuestion(question : HashMap<String, Any>) : Question
    {

        val questionOwner: String = question["questionOwner"] as String
        val questionType: String = question["questionType"] as String
        val questionBody: String = question["questionBody"] as String
        val categoryName: String = question["categoryName"] as String

        val answerVariants : ArrayList<Question.Answer> = ArrayList()

        for (answer in question["answerVariants"] as HashMap<String, Boolean>)
        {
            answerVariants.add(Question.Answer(answer.key, answer.value))
        }

        return Question (
                categoryName,
                questionType,
                questionBody,
                questionOwner,
                answerVariants
                )

    }

