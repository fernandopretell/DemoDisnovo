package com.limapps.base.support.model

import com.google.gson.annotations.SerializedName

data class ArticlesItem(@SerializedName("id") val id: String = "",
                        @SerializedName("icon") val icon: String = "",
                        @SerializedName("iconSelected") val iconSelected: String = "",
                        @SerializedName("url") val url: String = "",
                        @SerializedName("label_names") val labelNames: List<String> = emptyList(),
                        @SerializedName("name") val name: String = "",
                        @SerializedName("body") val body: String = "",
                        @SerializedName("created_at") val createdAt: String = "",
                        @SerializedName("edited_at") val editedAt: String = "",
                        @SerializedName("locale") val locale: String = "",
                        @SerializedName("source_locale") val sourceLocale: String = "",
                        @SerializedName("title") val title: String = "",
                        @SerializedName("updated_at") val updatedAt: String = "")