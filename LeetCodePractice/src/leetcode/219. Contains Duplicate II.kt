package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun containsNearbyDuplicate(nums: IntArray, k: Int): Boolean {
    val seen = mutableSetOf<Int>()
    nums.forEachIndexed { i, x ->
        if (seen.contains(x))
            return true
        seen.add(x)
        if (seen.size > k)
            seen.remove(nums[i - k])
    }
    return false
}

class containsNearbyDuplicateTests {
    @Test
    fun `should return true`() {
        assertEquals(
            true,
            containsNearbyDuplicate(intArrayOf(1, 2, 3, 1), 3)
        )
    }
    @Test
    fun `should return false`() {
        assertEquals(
            false,
            containsNearbyDuplicate(intArrayOf(1, 2, 3, 1), 1)
        )
    }
}