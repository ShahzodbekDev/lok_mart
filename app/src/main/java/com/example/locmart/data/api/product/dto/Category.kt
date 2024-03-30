package com.example.locmart.data.api.product.dto


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("count")
    val count: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String
)