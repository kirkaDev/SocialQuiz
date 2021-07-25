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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_own_question_fragment, container, false)
    }
}