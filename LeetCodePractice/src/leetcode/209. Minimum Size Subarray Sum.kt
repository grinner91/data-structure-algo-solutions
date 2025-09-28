package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.min

fun minSubArrayLen(target: Int, nums: IntArray): Int {
    var left = 0
    var currentSum = 0
    var minSubLen = Int.MAX_VALUE
    for (right in nums.indices) {
        currentSum += nums[right]
        while (currentSum >= target) {
            minSubLen = min(minSubLen, right - left + 1)
            currentSum -= nums[left]
            left++
        }
    }
    return if (minSubLen == Int.MAX_VALUE) 0 else minSubLen
}

class MinSubArrayLenTests {
    @Test
    fun `should return len 2 when given intgers and traget 7`() {
        assertEquals(
            2,
            minSubArrayLen(7, intArrayOf(2, 3, 1, 2, 4, 3))
        )
    }

    @Test
    fun `should return len 1 when given intgers and traget 4`() {
        assertEquals(
            0,
            minSubArrayLen(11, intArrayOf(1, 4, 4))
        )
    }

    @Test
    fun `should return len 0 when given intgers and traget 11`() {
        assertEquals(
            0,
            minSubArrayLen(11, intArrayOf(1, 1, 1, 1, 1, 1, 1, 1))
        )
    }
}