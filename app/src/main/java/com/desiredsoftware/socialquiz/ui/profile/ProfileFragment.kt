package com.desiredsoftware.socialquiz.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.Profile
import com.desiredsoftware.socialquiz.ui.components.CategoriesAdapter
import com.desiredsoftware.socialquiz.ui.components.ProfilePropertiesAdapter
import com.desiredsoftware.socialquiz.utils.generateCategories
import com.desiredsoftware.socialquiz.utils.generateProfile
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val profile : Profile
        profile = generateProfile()

        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val imageViewAvatar : ImageView? = root.findViewById(R.id.imageViewAvatar)
        val recyclerViewProfileProperties: RecyclerView = root.findViewById(R.id.recyclerViewProfileProperties)

        Picasso.get().load(profile.avatarURI)
                .resize(200, 200)
                .centerCrop()
                .into(imageViewAvatar)

        recyclerViewProfileProperties.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewProfileProperties.adapter = ProfilePropertiesAdapter(profile.propertiesArray)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}