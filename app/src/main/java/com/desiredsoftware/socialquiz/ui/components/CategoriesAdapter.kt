package com.desiredsoftware.socialquiz.ui.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.model.category.Category
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


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
        Picasso.get().load(categoriesList[position].imageResource)
            .resize(120, 120)
            .centerCrop()
            .into(holder.imageCategory, object : Callback {
                override fun onSuccess() {
                    holder.shimmer?.setShimmer(null)
                }

                override fun onError(e: Exception?) {
                    // TODO: Set placeholder here
                    holder.shimmer?.setShimmer(null)
                }
            })

        holder.textViewCategoryName?.text = categoriesList[position].categoryName
        holder.imageCategory?.setOnClickListener {
            onClickCategoryListener.onClicked(categoriesList[position].id)
        }
    }

    class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageCategory: ImageView? = null
        var textViewCategoryName: TextView? = null
        var shimmer: ShimmerFrameLayout? = null

        init {
            imageCategory = itemView.findViewById(R.id.imageCategory)
            textViewCategoryName = itemView.findViewById(R.id.textViewCategoryName)
            shimmer = itemView.findViewById(R.id.shimmer)
        }
    }
}