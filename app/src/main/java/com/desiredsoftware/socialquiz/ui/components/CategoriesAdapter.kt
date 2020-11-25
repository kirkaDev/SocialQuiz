package com.desiredsoftware.socialquiz.ui.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.QuestionCategory
import com.squareup.picasso.Picasso

class CategoriesAdapter (private val categoriesList: ArrayList<QuestionCategory>):
        RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>(){



    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_category, parent, false)
        return CategoriesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {

        Picasso.get().load(categoriesList.get(position).imageResource)
                .resize(120, 120)
                .centerCrop()
                .into(holder.imageCategory)

        holder.textViewCategoryName?.setText(categoriesList.get(position).categoryName)
    }


    class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var imageCategory: ImageView? = null
        var textViewCategoryName: TextView? = null

        init {
            imageCategory = itemView?.findViewById(R.id.imageCategory)
            textViewCategoryName = itemView?.findViewById(R.id.textViewCategoryName)
        }
    }


}