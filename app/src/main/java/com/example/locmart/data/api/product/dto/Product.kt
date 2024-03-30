package com.example.locmart.data.api.product.dto


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("discount")
    val discount: Double?,
    @SerializedName("favorite")
    var favorite: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("rating count")
    val ratingCount: Int,
    @SerializedName("title")
    val title: String
)