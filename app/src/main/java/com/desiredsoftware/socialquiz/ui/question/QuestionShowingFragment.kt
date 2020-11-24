package com.desiredsoftware.socialquiz.ui.question

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.desiredsoftware.socialquiz.R

class QuestionShowingFragment : Fragment() {

    companion object {
        fun newInstance() = QuestionShowingFragment()
    }

    private lateinit var viewModel: QuestionShowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.question_showing_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuestionShowingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}