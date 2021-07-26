package com.desiredsoftware.socialquiz.api.firebase.category

import com.desiredsoftware.socialquiz.model.category.Category

interface GetCategoriesCallback {
    fun onCallback(categories : ArrayList<Category>)
}