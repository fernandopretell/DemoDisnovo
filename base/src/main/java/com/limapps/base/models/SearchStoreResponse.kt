package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class SearchStoreResponse(
        @SerializedName("did_you_mean") val didYouMean: SearchModel.DidYouMean,
        @SerializedName("stores") val stores: List<SearchModel.SearchStore>)