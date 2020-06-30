package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class ServerConfig(@SerializedName(value = "server", alternate = ["url_server"])
                        val server: String,

                        @SerializedName("socket")
                        val socket: String? = null,

                        @SerializedName("phone_prefix")
                        val phonePrefix: String? = null,

                        @SerializedName("name")
                        val name: String? = null,

                        @SerializedName("code")
                        val code: String? = null,

                        @SerializedName("images")
                        val images: String? = null,

                        @SerializedName("push")
                        val push: Push? = null,

                        @SerializedName("centralized_server_host")
                        val serverCentralized: String,

                        @SerializedName("phone_verifier")
                        val phoneVerifier: String? = null,

                        @SerializedName(value = "services", alternate = ["url_microservice"])
                        val services: String,

                        @SerializedName(value = "support")
                        val support: String? = null,

                        @SerializedName("images_s3")
                        val imagesS3: String? = "https://images.rappi.com")
