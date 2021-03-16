package com.desiredsoftware.socialquiz.ui.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.question.Question
import com.desiredsoftware.socialquiz.utils.generateQuestion

class QuestionResultFragment : Fragment() {

    companion object {
        fun newInstance() = QuestionResultFragment()
    }

    //private lateinit var viewModel: QuestionResultViewModel


    val args: QuestionResultFragmentArgs by navArgs()

    lateinit var viewTryAgain: View
    lateinit var viewNextQuestion : View
    lateinit var viewChangeCategory : View
    lateinit var root : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_question_result, container, false)
        viewTryAgain = root.findViewById(R.id.buttonTryAgain)
        viewNextQuestion = root.findViewById(R.id.buttonNextQuestion)
        viewChangeCategory = root.findViewById(R.id.buttonChangeCategory)

        val buttonTryAgain : Button = viewTryAgain.findViewById(R.id.buttonWithIcon)
        val buttonNextQuestion : Button = viewNextQuestion.findViewById(R.id.buttonWithIcon)
        val buttonChangeCategory : Button = viewChangeCategory.findViewById(R.id.buttonWithIcon)


        var textviewNextQuestion: TextView = viewNextQuestion.findViewById(R.id.buttonWithIcon)
        textviewNextQuestion.setText(R.string.nextQuestion)

        var textviewChangeCategory: Button = viewChangeCategory.findViewById(R.id.buttonWithIcon)
        textviewChangeCategory.setText(R.string.changeCategory)


        buttonTryAgain.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val action = QuestionResultFragmentDirections.actionQuestionResultFragmentToQuestionShowingFragment(args.currentQuestion)
                val navController = requireParentFragment().findNavController()
                navController.navigate(action)
            }
        })

        buttonNextQuestion.setOnClickListener {
            // TODO: Replace for real method, at now imitation working
            val nextQuestion: Question = getNextQuestion()
            val action = QuestionResultFragmentDirections.actionQuestionResultFragmentToQuestionShowingFragment(nextQuestion)
            requireParentFragment().findNavController().navigate(action)
        }

        buttonChangeCategory.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val action = QuestionResultFragmentDirections.actionQuestionResultFragmentToNavigationHome()
                findNavController().navigate(action)
            }
        })
        if (args.answerIsCorrect) configureCorrectAnswer()
        else configureWrongAnswer()

        return root
    }

    fun configureWrongAnswer() {
        var textviewTryAgain: TextView = viewTryAgain.findViewById(R.id.buttonWithIcon)
        textviewTryAgain.setText(R.string.tryAgain)
    }

    fun configureCorrectAnswer() {
        val textViewResult : TextView = root.findViewById(R.id.textViewResult)
        textViewResult.setText(R.string.congrat)
        viewTryAgain.isVisible = false
    }

    fun getNextQuestion() : Question
    {
        return generateQuestion()
    }

}