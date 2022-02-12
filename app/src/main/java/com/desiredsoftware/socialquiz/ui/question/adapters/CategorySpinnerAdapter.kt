package com.desiredsoftware.socialquiz.ui.question.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckedTextView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.category.Category


class CustomSpinnerAdapter(
    val context: Context,
    val categoriesList: List<Category>,
    val onCheckedCategory: (category: Category) -> Unit,
    ) : BaseAdapter() {

    override fun getCount(): Int {
        return categoriesList.size
    }

    override fun getItem(position: Int): Any? {
        return categoriesList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_spinner, parent, false)

        view.findViewById<CheckedTextView>(R.id.spinnerItem).text = categoriesList[position].categoryName

        return view
    }
}