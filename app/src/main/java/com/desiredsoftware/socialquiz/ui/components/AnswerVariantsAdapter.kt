package com.desiredsoftware.socialquiz.ui.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.question.Question

class AnswerVariantsAdapter (private val answerVariantsList: ArrayList<Question.Answer>, private val onClickAnswerListener: OnClickAnswerListener) : RecyclerView.Adapter<AnswerVariantsAdapter.AnswersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswersViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_answer_variant, parent, false)
        return AnswerVariantsAdapter.AnswersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnswersViewHolder, position: Int) {
        holder.buttonAnswer?.text = answerVariantsList[position].answer

        holder.buttonAnswer?.setOnClickListener {
            onClickAnswerListener.onClicked(answerVariantsList[position])
        }
    }

    override fun getItemCount(): Int {
        return answerVariantsList.size
    }


    class AnswersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var buttonAnswer: Button? = null

        init {
            buttonAnswer = itemView.findViewById(R.id.buttonAnswerVariant)
        }
    }




}