package com.desiredsoftware.socialquiz.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.question.QuestionCategory
import com.desiredsoftware.socialquiz.ui.components.CategoriesAdapter
import com.desiredsoftware.socialquiz.ui.components.OnClickCategoryListener
import com.desiredsoftware.socialquiz.utils.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.textViewSelectCategory)
        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerViewCategory)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it

            recyclerView.layoutManager = GridLayoutManager(requireContext(),3)

            recyclerView.adapter = CategoriesAdapter(generateCategories(), object : OnClickCategoryListener{
                override fun onClicked(categoryId: String?) {
                    Log.d("RecyclerView clicked", "Category id = $categoryId was selected")
                }
            })
        })
        return root
    }

}