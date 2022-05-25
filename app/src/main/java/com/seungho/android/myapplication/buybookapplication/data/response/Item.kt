package com.seungho.android.myapplication.buybookapplication.data.response


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Item(
    @SerializedName("author")
    val author: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("discount")
    val discount: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("isbn")
    val isbn: String?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("pubdate")
    val pubdate: String?,
    @SerializedName("publisher")
    val publisher: String?,
    @SerializedName("title")
    val title: String?
) : Serializable