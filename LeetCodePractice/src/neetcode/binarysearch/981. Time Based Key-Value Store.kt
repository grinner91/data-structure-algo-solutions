package neetcode.binarysearch

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class TimeMapTreeMap() {
    private val store = HashMap<String, TreeMap<Int, String>>()
    fun set(key: String, value: String, timestamp: Int) {
        val tm = store.getOrPut(key) { TreeMap() }
        tm[timestamp] = value
    }

    fun get(key: String, timestamp: Int): String {
        val tm = store[key] ?: return ""
        val ent = tm.floorEntry(timestamp) ?: return ""
        return ent.value
    }
}

class TimeMapBinarySearch {
    private val store = HashMap<String, MutableList<Pair<String, Int>>>()
    fun set(key: String, value: String, timestamp: Int) {
        val list = store.getOrPut(key) { mutableListOf() }
        list.add(Pair(value, timestamp))
    }

    fun get(key: String, timestamp: Int): String {
        val list = store[key] ?: return ""
        var l = 0
        var r = list.lastIndex
        var res = -1
        while (l <= r) {
            val m = l + (r - l) / 2
            when {
                list[m].second <= timestamp -> {
                    res = m
                    l = m + 1
                }

                else -> {
                    r = m - 1
                }
            }
        }
        return if (res == -1) "" else list[res].first
    }
}

class TimeMap981Test {

    private interface TimeMapApi {
        fun set(key: String, value: String, timestamp: Int)
        fun get(key: String, timestamp: Int): String
    }

    private class WrapTreeMap : TimeMapApi {
        private val impl = TimeMapTreeMap()
        override fun set(key: String, value: String, timestamp: Int) = impl.set(key, value, timestamp)
        override fun get(key: String, timestamp: Int): String = impl.get(key, timestamp)
    }

    private class WrapBinarySearch : TimeMapApi {
        private val impl = TimeMapBinarySearch()
        override fun set(key: String, value: String, timestamp: Int) = impl.set(key, value, timestamp)
        override fun get(key: String, timestamp: Int): String = impl.get(key, timestamp)
    }

    // Your preferred style: list of function references (factory fns here)
    private val impls = listOf(
        ::WrapTreeMap,
        ::WrapBinarySearch
    )

    @Test
    fun exampleCase() {
        impls.forEach { make ->
            val tm = make()
            tm.set("foo", "bar", 1)
            assertEquals("bar", tm.get("foo", 1))
            assertEquals("bar", tm.get("foo", 3))
            tm.set("foo", "bar2", 4)
            assertEquals("bar2", tm.get("foo", 4))
            assertEquals("bar2", tm.get("foo", 5))
        }
    }

    @Test
    fun returnsEmptyWhenKeyMissing() {
        impls.forEach { make ->
            val tm = make()
            assertEquals("", tm.get("missing", 10))
        }
    }

    @Test
    fun returnsEmptyWhenTimestampBeforeFirst() {
        impls.forEach { make ->
            val tm = make()
            tm.set("a", "x", 5)
            assertEquals("", tm.get("a", 4))
            assertEquals("", tm.get("a", 0))
        }
    }

    @Test
    fun exactTimestampAndInBetween() {
        impls.forEach { make ->
            val tm = make()
            tm.set("k", "v1", 10)
            tm.set("k", "v2", 20)
            tm.set("k", "v3", 30)

            assertEquals("v1", tm.get("k", 10))
            assertEquals("v1", tm.get("k", 19))
            assertEquals("v2", tm.get("k", 20))
            assertEquals("v2", tm.get("k", 29))
            assertEquals("v3", tm.get("k", 30))
            assertEquals("v3", tm.get("k", 100))
        }
    }

    @Test
    fun multipleKeysIndependent() {
        impls.forEach { make ->
            val tm = make()
            tm.set("a", "a1", 1)
            tm.set("b", "b1", 2)
            tm.set("a", "a2", 3)

            assertEquals("a1", tm.get("a", 2))
            assertEquals("a2", tm.get("a", 3))
            assertEquals("b1", tm.get("b", 2))
            assertEquals("", tm.get("b", 1))
        }
    }

    @Test
    fun overwritingSameTimestamp() {
        impls.forEach { make ->
            val tm = make()
            tm.set("x", "v1", 5)
            tm.set("x", "v2", 5) // same timestamp: latest should win

            assertEquals("v2", tm.get("x", 5))
            assertEquals("v2", tm.get("x", 6))
            assertEquals("", tm.get("x", 4))
        }
    }
}