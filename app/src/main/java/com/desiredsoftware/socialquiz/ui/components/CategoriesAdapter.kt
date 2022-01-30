package com.desiredsoftware.socialquiz.ui.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.model.category.Category
import com.facebook.shimmer.ShimmerFrameLayout

class CategoriesAdapter(
    private val categoriesList: ArrayList<Category>,
    private val onClickCategoryListener: OnClickCategoryListener
) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoriesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(categoriesList[position])
    }

    inner class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var shimmer: ShimmerFrameLayout = itemView.findViewById(R.id.shimmer)
        var imageCategory: ImageView = itemView.findViewById(R.id.imageCategory)
        var textViewCategoryName: TextView? = itemView.findViewById(R.id.textViewCategoryName)

        fun bind(category: Category) {
            shimmer.setShimmer(null)
            Glide.with(itemView).load(category.imageResource).into(imageCategory)
            textViewCategoryName?.text = category.categoryName

            imageCategory.setOnClickListener {
                onClickCategoryListener.onClicked(category.id)
            }
        }
    }
}