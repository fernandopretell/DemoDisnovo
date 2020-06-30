package com.limapps.base.models

import com.google.gson.JsonArray
import com.google.gson.JsonObject

sealed class GoogleDirections {

    data class Route(val previewPolyline: String? = null,
                     val legs: List<Leg?>? = null) {
        companion object Factory {
            fun fromJsonObject(routeNode: JsonObject?): Route {
                return routeNode?.let {
                    val legs = it.getAsJsonArray("legs")
                    val polyline = it.getAsJsonObject("overview_polyline")?.get("points")?.asString
                    Route(polyline, getLegsFromJsonObject(legs))
                } ?: Route()
            }

            private fun getLegsFromJsonObject(legs: JsonArray): List<Leg> {
                val legsFromArray = mutableListOf<Leg>()
                legs.forEach {
                    legsFromArray.add(Leg.fromJsonObject(it.asJsonObject.getAsJsonArray("steps")))
                }
                return legsFromArray
            }
        }
    }

    data class Leg(val steps: MutableList<Step> = mutableListOf()) {
        companion object Factory {
            fun fromJsonObject(jsonArray: JsonArray?): Leg {
                return jsonArray?.let {
                    Leg(it.map { element -> Step.fromJsonObject(element as JsonObject?) }.toMutableList())
                } ?: Leg()
            }
        }
    }

    data class Step(val polyline: String? = null) {
        companion object Factory {
            fun fromJsonObject(jsonObject: JsonObject?): Step {
                return jsonObject?.let {
                    Step(it.getAsJsonObject("polyline")?.get("points")?.asString)
                } ?: Step()
            }
        }
    }
}
