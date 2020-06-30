package com.limapps.base.helpers

import com.limapps.common.countOccurrencesOf
import com.limapps.common.getIndexesEnclosedSentences
import org.junit.Assert.assertEquals
import org.junit.Test

class StringExtensionsTest {

    val startToken = "[["
    val endToken = "]]"

    @Test
    fun `Get indexes for enclosed sentences around startToken and endToken`() {
        val testString = "[[Welcome]] to this simple test [[method]]"
        val validIndexes = arrayListOf(0, 7, 28, 34)

        val (newString, indexes) = testString.getIndexesEnclosedSentences(startToken, endToken)

        assertEquals(indexes.size, testString.countOccurrencesOf(startToken) + testString.countOccurrencesOf(endToken))
        assertEquals(newString, testString.replace(startToken, "").replace(endToken, ""))
        assertEquals(indexes, validIndexes)
    }

    @Test
    fun `Get zero indexes for an empty String without startToken or endToken`() {
        val emptyString = ""
        val validIndexes = emptyList<Int>()

        val (newString, indexes) = emptyString.getIndexesEnclosedSentences(startToken, endToken)

        assertEquals(indexes.size, validIndexes.size)
        assertEquals(newString, emptyString.replace(startToken, "").replace(endToken, ""))
        assertEquals(indexes, validIndexes)
    }
}