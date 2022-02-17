package com.desiredsoftware.socialquiz.ui.question.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.question.Answer

class OwnAnswerVariantsAdapter(var answerVariants: MutableList<Answer>) :
    RecyclerView.Adapter<OwnAnswerVariantsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_own_question_answer_variant, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(answerVariants[position], position)
    }

    override fun getItemCount(): Int {
        return answerVariants.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(answerVariant: Answer, position: Int) {
            val answerEditText =
                itemView.findViewById<AppCompatEditText>(R.id.answerVariantEditText)
            val answerLabel = itemView.findViewById<AppCompatTextView>(R.id.answerLabel)

            val answerIsCorrectButton = itemView.findViewById<AppCompatButton>(R.id.isCorrectButton)

            answerLabel.text =
                itemView.context.getString(R.string.answer_number, (position + 1).toString())
            answerEditText.hint = itemView.context.getString(R.string.answer_variant_number)

            answerEditText.addTextChangedListener {
                answerVariant.answer = it.toString()
            }

            answerIsCorrectButton.setOnClickListener {
                answerVariant.isCorrect = true
                answerVariants.forEach { answer ->
                    if (answer != answerVariant) {
                        answer.isCorrect = false
                    }
                }
            }
        }
    }
}
