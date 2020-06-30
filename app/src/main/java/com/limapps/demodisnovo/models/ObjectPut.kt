package com.limapps.demodisnovo.models

data class ObjectPut (val elements: List<WigdetProperty>,
                        val id: Int)

data class WigdetProperty(val nameWidget: String, val valueWidget: String)