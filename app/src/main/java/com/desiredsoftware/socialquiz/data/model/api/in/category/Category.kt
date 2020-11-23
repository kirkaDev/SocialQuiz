package com.desiredsoftware.socialquiz.data.model.api.`in`.category

class Category constructor (imageResource : String, categoryName: String)
{
    val imageResource: String
    val categoryName : String
    val categoryId: Int

    init {
        this.imageResource = imageResource
        this.categoryName = categoryName
        this.categoryId = 0
    }
}