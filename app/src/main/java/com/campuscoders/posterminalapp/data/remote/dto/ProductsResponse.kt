package com.campuscoders.posterminalapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("products")
    val products: List<ProductData>? = null
)

data class ProductData(
    @SerializedName("product_id")
    val productId: Int? = null,
    
    @SerializedName("product_category_id")
    val productCategoryId: String? = null,
    
    @SerializedName("product_name")
    val productName: String? = null,
    
    @SerializedName("product_code")
    val productCode: String? = null,
    
    @SerializedName("product_description")
    val productDescription: String? = null,
    
    @SerializedName("product_image")
    val productImage: String? = null,
    
    @SerializedName("product_barcode")
    val productBarcode: String? = null,
    
    @SerializedName("product_price")
    val productPrice: String? = null,
    
    @SerializedName("product_price_cents")
    val productPriceCents: String? = null,
    
    @SerializedName("product_kdv")
    val productKdv: String? = null,
    
    @SerializedName("product_stappage")
    val productStappage: String? = null,
    
    @SerializedName("product_quantity")
    val productQuantity: String? = null,
    
    @SerializedName("product_discount")
    val productDiscount: String? = null,
    
    @SerializedName("product_exception_code")
    val productExceptionCode: String? = null,
    
    @SerializedName("product_exception_description")
    val productExceptionDescription: String? = null
)
