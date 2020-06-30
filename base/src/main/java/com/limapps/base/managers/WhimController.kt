package com.limapps.base.managers

import com.limapps.base.models.PlaceSorted
import com.limapps.base.models.Whim
import com.limapps.base.utils.Optional

interface WhimController {
    fun getCurrentWhim(): Optional<Whim>
    fun getCurrentWhimList(): List<Whim>
    fun insertWhim(whim: Whim)
    fun getTemporalWhim(): TemporalWhim
    fun saveTemporalWhim(placeSorted: PlaceSorted?, description: String)
    fun clearWhims()
    fun deleteWhimsInformation()
    fun loadImagesFromWhim(whim: Whim): List<String>

    fun saveTemporalWhimPlace(placeSorted: PlaceSorted?)
    fun saveTemporalWhimDescription(description: String)
    fun saveTemporalWhimImage(imageUrl: String)
    fun saveTemporalWhimEstimatedIndex(index: Int)
}

data class TemporalWhim(
        val place: PlaceSorted?,
        val description: String,
        val image: String,
        val priceIndex: Int
)