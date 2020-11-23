package com.desiredsoftware.socialquiz.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.api.`in`.category.Category
import com.desiredsoftware.socialquiz.ui.components.CategoriesAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    val categoriesList = ArrayList<Category>()

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

            categoriesList.add(Category("https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
            categoriesList.add(Category("https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))
            categoriesList.add(Category("https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
            categoriesList.add(Category("https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))
            categoriesList.add(Category("https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
            categoriesList.add(Category("https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))
            categoriesList.add(Category("https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
            categoriesList.add(Category("https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))
            categoriesList.add(Category("https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
            categoriesList.add(Category("https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))
            categoriesList.add(Category("https://avatars.mds.yandex.net/get-zen_doc/15270/pub_5a9e407b830905db0f61b7bf_5a9e40d0610493f240e19a18/scale_1200", "Sunflower"))
            categoriesList.add(Category("https://vsthemes.ru/uploads/posts/2018-12/1582030596_las-vegas_vsthemes_ru-34.jpg", "Las-Vegas"))

            //recyclerView.layoutManager = LinearLayoutManager(requireContext())
            //recyclerView.adapter = CategoriesAdapter(categoriesList)

            recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
            recyclerView.adapter = CategoriesAdapter(categoriesList)

        })
        return root
    }
}