package com.limapps.base.support.model

import com.google.gson.annotations.SerializedName

data class RequestTicket(
        @SerializedName("comment") var comment: CommentRequest? = CommentRequest(),
        @SerializedName("custom_fields") var customFields: List<CustomField?> = listOf(),
        @SerializedName("requester") val requester: Requester = Requester(),
        @SerializedName("subject") val subject: String?,
        @SerializedName("tags") val tags: List<String?>?
)

data class CustomField(
        @SerializedName("key") val key: String,
        @SerializedName("value") val value: Any?
)

data class Requester(
        @SerializedName("email") var email: String? = "",
        @SerializedName("name") var name: String? = ""
)

data class CommentRequest(
        @SerializedName("body") var body: String? = "",
        @SerializedName("uploads") var uploads: List<String?>? = null
)