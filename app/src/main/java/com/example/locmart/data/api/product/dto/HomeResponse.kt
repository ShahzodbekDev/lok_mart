package com.example.locmart.data.api.product.dto


import com.example.locmart.data.api.auth.dto.UserDto
import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("banners")
    val banners: List<Banner>,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("sections")
    val sections: List<Section>,
    @SerializedName("user")
    val user: UserDto
)