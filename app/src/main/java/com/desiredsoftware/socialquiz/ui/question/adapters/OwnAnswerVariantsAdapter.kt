package com.desiredsoftware.socialquiz.ui.question.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.question.Answer

class OwnAnswerVariantsAdapter(val answerVariants: MutableList<Answer>):
    RecyclerView.Adapter<OwnAnswerVariantsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_own_question_answer_variant, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(answerVariants[position], position)
    }

    override fun getItemCount(): Int {
        return answerVariants.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(answerVariant: Answer, position: Int){
            val answerEditText = itemView.findViewById<AppCompatEditText>(R.id.answerVariantEditText)
            val answerLabel = itemView.findViewById<AppCompatTextView>(R.id.answerLabel)

            answerLabel.text = itemView.context.getString(R.string.answer_number, (position+1).toString())
            answerEditText.hint = itemView.context.getString(R.string.answer_variant_number, (position+1).toString())
        }
    }
}