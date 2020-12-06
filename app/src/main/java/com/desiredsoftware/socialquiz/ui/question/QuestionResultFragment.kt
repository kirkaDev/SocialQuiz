package com.desiredsoftware.socialquiz.ui.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.desiredsoftware.socialquiz.R

class QuestionResultFragment : Fragment() {

    companion object {
        fun newInstance() = QuestionResultFragment()
    }

    private lateinit var viewModel: QuestionResultViewModel

    val args: QuestionResultFragmentArgs by navArgs()

    lateinit var root : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_question_result, container, false)

        val buttonNextQuestion: View = root.findViewById(R.id.buttonNextQuestion)
        var textviewNextQuestion: TextView = buttonNextQuestion.findViewById(R.id.textView)
        textviewNextQuestion.setText(R.string.nextQuestion)

        val buttonChangeCategory: View = root.findViewById(R.id.buttonChangeCategory)
        var textviewChangeCategory: TextView = buttonChangeCategory.findViewById(R.id.textView)
        textviewChangeCategory.setText(R.string.changeCategory)



        if (args.answerIsCorrect)
        {
            configureCorrectAnswer()
        }
        else {
            configureWrongAnswer()
        }

        return root

    }

/*    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuestionResultViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

    fun configureWrongAnswer() {
        val viewTryAgain: View = root.findViewById(R.id.buttonTryAgain)
        var textviewTryAgain: TextView = viewTryAgain.findViewById(R.id.textView)
        textviewTryAgain.setText(R.string.tryAgain)
    }

    fun configureCorrectAnswer() {
        val textViewResult : TextView = root.findViewById(R.id.textViewResult)
        textViewResult.setText(R.string.congrat)
        val viewTryAgain: View = root.findViewById(R.id.buttonTryAgain)
        viewTryAgain.isVisible = false
    }


}