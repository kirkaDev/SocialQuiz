package com.desiredsoftware.socialquiz.ui.question

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.desiredsoftware.socialquiz.R

class QuestionResultFragment : Fragment() {

    companion object {
        fun newInstance() = QuestionResultFragment()
    }

    private lateinit var viewModel: QuestionResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question_result, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuestionResultViewModel::class.java)
        // TODO: Use the ViewModel
    }

}