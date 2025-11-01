package com.campuscoders.posterminalapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("categories")
    val categories: List<CategoryData>? = null
)

data class CategoryData(
    @SerializedName("category_id")
    val categoryId: Int? = null,
    
    @SerializedName("category_code")
    val categoryCode: String? = null,
    
    @SerializedName("category_name")
    val categoryName: String? = null,
    
    @SerializedName("category_description")
    val categoryDescription: String? = null,
    
    @SerializedName("category_image")
    val categoryImage: String? = null
)
