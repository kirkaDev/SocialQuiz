package com.desiredsoftware.socialquiz.ui.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.desiredsoftware.socialquiz.R

class AddOwnQuestionFragment : Fragment() {

    companion object {
        fun newInstance() = AddOwnQuestionFragment()
    }

    private lateinit var viewModel: AddOwnQuestionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_own_question_fragment, container, false)
    }

/*    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddOwnQuestionViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

}