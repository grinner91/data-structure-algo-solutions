package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.max

fun maxArea(heights: IntArray): Int {
    var maxArea = 0
    var left = heights.indices.first
    var right = heights.indices.last
    while (left < right) {
        val currentArea = minOf(heights[left], heights[right]) * (right - left)
        maxArea = max(maxArea, currentArea)
        if (heights[left] < heights[right]) left++
        else right--
    }
    return maxArea
}

class MaxAreaTest {
    @Test
    fun `should return max_49 when heights`() {
        assertEquals(
            49,
            maxArea(intArrayOf(1, 8, 6, 2, 5, 4, 8, 3, 7))
        )
    }

    @Test
    fun `should return max_1 when heights`() {
        assertEquals(
            1,
            maxArea(intArrayOf(1, 8))
        )
    }
}