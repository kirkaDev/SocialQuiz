package com.desiredsoftware.socialquiz.ui.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.model.question.Answer

class AnswersAdapter (private val answersList: List<Answer>,
                      private val onClickAnswerListener: OnClickAnswerListener) : RecyclerView.Adapter<AnswersAdapter.AnswersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswersViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_answer_variant, parent, false)
        return AnswersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnswersViewHolder, position: Int) {
        holder.buttonAnswer?.text = answersList[position].answer

        holder.buttonAnswer?.setOnClickListener {
            onClickAnswerListener.onClicked(answer = answersList[position])
        }
    }

    override fun getItemCount(): Int {
        return answersList.size
    }


    class AnswersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var buttonAnswer: Button? = null

        init {
            buttonAnswer = itemView.findViewById(R.id.buttonAnswer)
        }
    }




}