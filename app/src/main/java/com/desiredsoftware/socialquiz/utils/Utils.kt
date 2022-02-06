package com.desiredsoftware.socialquiz.utils

import com.desiredsoftware.socialquiz.data.model.category.Category
import com.desiredsoftware.socialquiz.data.model.question.Answer
import com.desiredsoftware.socialquiz.data.model.question.Question

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

fun generateQuestion(): Question {
    val answerVariants: List<Answer> = emptyList()

    val answerMutableList = answerVariants.toMutableList()
    answerMutableList.add(Answer("Cat", true))
    answerMutableList.add(Answer("Shake", false))
    answerMutableList.add(Answer("Dolphin", false))
    answerMutableList.add(Answer("Cat", true))
    answerMutableList.add(Answer("Shake", false))
    answerMutableList.add(Answer("Dolphin", false))

    return Question(
        questionId = "23",
        categoryId = "2",
        answerVariants = answerMutableList,
        questionBody = generateMediaURI(),
        questionAuthorUid = "Nikolay Drozdov",
        questionType = Question.Companion.QUESTION_TYPE.TEXT
    )
}

    fun generateMediaURI() : String
    {
        return "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4"
    }

