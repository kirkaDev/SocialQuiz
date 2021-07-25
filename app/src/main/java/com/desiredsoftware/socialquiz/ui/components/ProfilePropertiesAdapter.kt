package com.desiredsoftware.socialquiz.ui.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.model.profile.Profile

class ProfilePropertiesAdapter (private val properties: ArrayList<Profile.ProfileProperty>) :
        RecyclerView.Adapter<ProfilePropertiesAdapter.PropertiesViewHolder>()
{

    class PropertiesViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var propertyName : TextView? = null
        var propertyValue: TextView? = null

        init {
            propertyName = itemView.findViewById(R.id.propertyName)
            propertyValue = itemView.findViewById(R.id.propertyValue)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertiesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_profile_property, parent, false)
        return ProfilePropertiesAdapter.PropertiesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PropertiesViewHolder, position: Int) {
        holder.propertyName?.setText(properties.get(position).propertyName)
        holder.propertyValue?.setText(properties.get(position).propertyValue)
    }

    override fun getItemCount(): Int {
        return properties.size
    }


}