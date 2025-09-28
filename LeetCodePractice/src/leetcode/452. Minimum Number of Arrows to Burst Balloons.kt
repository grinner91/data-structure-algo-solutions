package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun findMinArrowShots(points: Array<IntArray>): Int {
    if (points.isEmpty()) return 0
    points.sortBy { it[1] }
    var arrows = 1
    var currentEnd = points[0][1]
    for (i in 1 until points.size) {
        if (currentEnd < points[i][0]) {
            arrows++
            currentEnd = points[i][1]
        }
    }
    return arrows
}

class FindMinArrowShotsTest {

    @Test
    fun testFindMinArrowShotsEmptyInput() {
        val points: Array<IntArray> = arrayOf()
        val result = findMinArrowShots(points)
        assertEquals(0, result)
    }

    @Test
    fun testFindMinArrowShotsSinglePoint() {
        val points: Array<IntArray> = arrayOf(intArrayOf(1, 2))
        val result = findMinArrowShots(points)
        assertEquals(1, result)
    }

    @Test
    fun testFindMinArrowShotsMultiplePoints() {
        val points: Array<IntArray> = arrayOf(
            intArrayOf(10, 16),
            intArrayOf(2, 8),
            intArrayOf(1, 6),
            intArrayOf(7, 12)
        )
        val result = findMinArrowShots(points)
        assertEquals(2, result)
    }
}


