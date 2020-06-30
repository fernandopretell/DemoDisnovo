package com.limapps.base.helpers

class RatioCalculator(val horizontal: Int, val vertical: Int) {

    fun getHeight(width: Int) = width * vertical / horizontal

    fun getWidth(height: Int) = height * horizontal / vertical
}