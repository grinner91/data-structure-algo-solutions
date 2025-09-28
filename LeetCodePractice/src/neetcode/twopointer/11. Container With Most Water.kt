package neetcode.twopointer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.max
import kotlin.math.min

fun maxArea(height: IntArray): Int {
    var left = 0
    var right = height.size - 1
    var maxArea = 0
    var currentArea: Int
    while (left < right) {
        currentArea = min(height[right], height[left]) * (right - left)
        maxArea = max(currentArea, maxArea)
        if (height[left] > height[right]) right--
        else left++
    }
    return maxArea
}


class MaxAreaTest {

    @Test
    fun `should return max area`() {
        assertEquals(49, maxArea(intArrayOf(1, 8, 6, 2, 5, 4, 8, 3, 7)))
    }
}