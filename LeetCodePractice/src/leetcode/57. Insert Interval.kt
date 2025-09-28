package leetcode

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.math.max

fun insert(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {

    val intervalsList = intervals.toMutableList()
    intervalsList.add(newInterval)
    intervalsList.sortBy { it[0] }

    val mergedList = mutableListOf<IntArray>()
    mergedList.add(intervalsList[0])

    intervalsList.drop(0).forEach { current ->
        if (current[0] <= mergedList.last()[1]) {
            mergedList.last()[1] = max(current[1], mergedList.last()[1])
        } else {
            mergedList.add(current)
        }
    }
    return mergedList.toTypedArray()
}

class InsertIntervalsTests {

    @Test
    fun test1() {
        val inputs = arrayOf(
            intArrayOf(1, 2),
            intArrayOf(3, 5),
            intArrayOf(8, 10),
            intArrayOf(12, 16),
        )
        val exepected = arrayOf(
            intArrayOf(1, 2),
            intArrayOf(3, 10),
            intArrayOf(12, 16),
        )
        val newInterval = intArrayOf(4, 8)
        val actual = insert(inputs, newInterval)
        Assertions.assertTrue(
            exepected.contentDeepEquals(actual)
        )
    }


    @Test
    fun test2() {
        val inputs = arrayOf(
            intArrayOf(1, 3),
            intArrayOf(6, 9),
        )
        val exepected = arrayOf(
            intArrayOf(1, 5),
            intArrayOf(6, 9),

            )
        val newInterval = intArrayOf(2, 5)
        val actual = insert(inputs, newInterval)
        Assertions.assertTrue(
            exepected.contentDeepEquals(actual)
        )
    }
}