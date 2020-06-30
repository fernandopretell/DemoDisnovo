package com.limapps.base.remoteconfig

/**
 * Wrapper for a Remote Config parameter value, with methods to get it as different types.
 */
interface RemoteConfigValue {

    /**
     * Gets the value as a Long.
     *
     * @param default The default value in case there is a problem with the parsing
     *
     * @return Long
     */
    @Throws(IllegalArgumentException::class)
    fun asLong(default: Long = 0): Long

    /**
     * Gets the value as a Double.
     * @return Double
     * @throws IllegalArgumentException If the value cannot be converted to a Double.
     */
    @Throws(IllegalArgumentException::class)
    fun asDouble(): Double

    /**
     * Gets the value as a String.
     * @return String
     */
    fun asString(): String

    /**
     * Gets the value as a ByteArray.
     * @return ByteArray
     * @throws IllegalArgumentException If the value cannot be converted to a ByteArray.
     */
    fun asByteArray(): ByteArray

    /**
     * Gets the value as a Boolean.
     * @return Boolean
     * @throws IllegalArgumentException If the value cannot be converted to a Boolean.
     */
    @Throws(IllegalArgumentException::class)
    fun asBoolean(default: Boolean = false): Boolean


    /**
     * Gets the value as Json
     * @return T
     * @throws IllegalArgumentException If the value cannot be converted to a Json.
     */
    @Throws(IllegalArgumentException::class)
    fun <T> asJson(clazz: Class<T>): T?
}