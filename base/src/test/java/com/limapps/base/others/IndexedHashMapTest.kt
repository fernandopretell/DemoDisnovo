package com.limapps.base.others

import com.limapps.common.IndexedHashMap
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class IndexedHashMapTest {

    private lateinit var indexedHashMap: IndexedHashMap<Any, Any>

    @Before
    fun setupNewIndexedHashMap() {
        indexedHashMap = IndexedHashMap()
    }

    @Test
    fun `IndexedHashMap new instance is not null`() {
        assertThat(indexedHashMap, notNullValue())
    }

    @Test
    fun `Add single value into IndexedHashMap`() {
        assertThat(indexedHashMap, notNullValue())
        assert(indexedHashMap.isEmpty())

        indexedHashMap[FIRST_PAIR.first] = FIRST_PAIR.second

        assert(indexedHashMap.isNotEmpty())
        assert(indexedHashMap.size == 1)
        assert(indexedHashMap[FIRST_PAIR.first] == FIRST_PAIR.second)
    }

    @Test
    fun `Add value at the end of IndexedHashMap`() {
        assertThat(indexedHashMap, notNullValue())
        assert(indexedHashMap.isEmpty())
        populateIndexedHashMap()
        assert(indexedHashMap.size == 2)
        assert(indexedHashMap.isNotEmpty())

        indexedHashMap[THIRD_PAIR.first] = THIRD_PAIR.second

        assert(indexedHashMap.size == 3)
        assert(indexedHashMap[THIRD_PAIR.first] == THIRD_PAIR.second)
        assert(indexedHashMap.getAtIndex(indexedHashMap.size - 1) == THIRD_PAIR.second)
    }

    @Test
    fun `Insert HashMap correctly to IndexedHashMap`() {
        assertThat(indexedHashMap, notNullValue())
        assert(indexedHashMap.isEmpty())

        indexedHashMap.putAll(TWO_ELEMENT_MAP)

        assert(indexedHashMap.size == TWO_ELEMENT_MAP.size)
        assert(indexedHashMap.contains(FIRST_PAIR.first))
        assert(indexedHashMap.contains(SECOND_PAIR.first))
    }

    @Test
    fun `Get correct index for last element added`() {
        assertThat(indexedHashMap, notNullValue())
        assert(indexedHashMap.isEmpty())

        indexedHashMap[FIRST_PAIR.first] = FIRST_PAIR.second
        indexedHashMap[SECOND_PAIR.first] = SECOND_PAIR.second

        assert(indexedHashMap.getIndex(FIRST_PAIR.first) == 0)
        assert(indexedHashMap.getIndex(SECOND_PAIR.first) == 1)
    }

    private fun populateIndexedHashMap() {
        indexedHashMap[FIRST_PAIR.first] = FIRST_PAIR.second
        indexedHashMap[SECOND_PAIR.first] = SECOND_PAIR.second
    }

    companion object {
        val FIRST_PAIR = "Key1" to "Value1"
        val SECOND_PAIR = "Key2" to "Value2"
        val THIRD_PAIR = "Key3" to "Value3"

        val TWO_ELEMENT_MAP = HashMap<Any, Any>().apply {
            this[FIRST_PAIR.first] = FIRST_PAIR.second
            this[SECOND_PAIR.first] = SECOND_PAIR.second
        }
    }
}