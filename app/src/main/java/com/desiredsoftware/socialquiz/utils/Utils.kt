package com.desiredsoftware.socialquiz.utils

import com.desiredsoftware.socialquiz.model.category.Category
import com.desiredsoftware.socialquiz.model.question.Answer
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

    fun generateQuestion() : Question
    {
        val answerVariants : List<Answer> = emptyList()

        val answerMutableList = answerVariants.toMutableList()
        answerMutableList.add(Answer("Cat", true))
        answerMutableList.add(Answer("Shake", false))
        answerMutableList.add(Answer("Dolphin", false))

        val question = Question(
            mAnswers = answerMutableList,
            mCategoryName = "Animals",
            mCategory_id = "AnimalsCategory_id",
            mLanguage = "ru",
            mQuestionBody = generateMediaURI(),
            mQuestionOwner = "Nikolay Drozdov",
       )
        question.mQuestionType = "text"

        return question
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

        val answerVariants : ArrayList<Answer> = ArrayList()

/*        for (answer in question["answerVariants"] as HashMap<String, Boolean>)
        {
            answerVariants.add(Answer(answer.key, answer.value))
        }*/

/*        return Question(
            mAnswerVariants = answerVariants,
            mCategoryName = "Animals",
            mCategory_id = "AnimalsCategory_id",
            mLanguage = "ru",
            mQuestionBody = generateMediaURI(),
            mQuestionOwner = "Nikolay Drozdov",
            mQuestionType = "video"
            )*/

        return generateQuestion()
    }

