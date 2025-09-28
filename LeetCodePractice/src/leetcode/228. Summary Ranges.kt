package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun summaryRanges(nums: IntArray): List<String> {
    val ranges = mutableListOf<String>()
    var right = 1
    var left = 0
    while (right <= nums.size) {
        if (right == nums.size || nums[right - 1] + 1 != nums[right]) {
            if (right - left == 1) ranges.add("${nums[left]}")
            else ranges.add("${nums[left]}->${nums[right - 1]}")
            left = right
        }
        right++
    }
    return ranges
}

class summaryRangesTests {
    @Test
    fun `should return correct ans`() {
        assertEquals(
            listOf("0->2", "4->5", "7"),
            summaryRanges(intArrayOf(0, 1, 2, 4, 5, 7))
        )
    }
}