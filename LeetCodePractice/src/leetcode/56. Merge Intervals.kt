package leetcode

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

fun merge(intervals: Array<IntArray>): Array<IntArray> {
    intervals.sortBy { it[0] }
    val merged = mutableListOf<IntArray>()
    merged.add(intervals[0])
    intervals.drop(0)
        .forEach { current ->
            if (merged.last()[1] >= current[0]) {
                merged.last()[1] = maxOf(merged.last()[1], current[1])
            } else {
                merged.add(current)
            }
        }
    return merged.toTypedArray()
}

class MergeTests {

    @Test
    fun test1() {
        val inputs = arrayOf(
            intArrayOf(1, 3),
            intArrayOf(2, 6),
            intArrayOf(8, 10),
            intArrayOf(15, 18),
        )
        val exepected = arrayOf(
            intArrayOf(1, 6),
            intArrayOf(8, 10),
            intArrayOf(15, 18),
        )
        val actual = merge(inputs)
        assertTrue(
            exepected.contentDeepEquals(actual)
        )
    }

}