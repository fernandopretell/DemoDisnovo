package com.limapps.base.support.model

import com.google.gson.annotations.SerializedName

data class NewSection(@SerializedName("name") val name: String = "",
                      @SerializedName("id") val id: String = "",
                      @SerializedName("articles") val articles: List<ArticlesItem>?)