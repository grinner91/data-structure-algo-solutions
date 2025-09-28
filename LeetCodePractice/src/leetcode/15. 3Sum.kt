package leetcode

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

fun threeSum(nums: IntArray): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    nums.sort()
    for (i in nums.indices.first..nums.indices.last) {
        if (i == 0 || nums[i] != nums[i - 1])
            findTriplet(nums, i, result)
    }
    return result
}

internal fun findTriplet(nums: IntArray, i: Int, result: MutableList<List<Int>>) {
    var left = i + 1
    var right = nums.indices.last
    while (left < right) {
        val currentSum = nums[i] + nums[left] + nums[right]
        if (currentSum == 0) {
            result.add(listOf(nums[i], nums[left], nums[right]))
            left++
            right--
            while (left < right && nums[left] == nums[left - 1]) left++
        } else if (currentSum < 0) left++
        else right--
    }
}

class ThreeSumTests {
    @Test
    fun `should return 2_triplets_list when given nums`() {
        val expected = listOf(listOf(-1, -1, 2), listOf(-1, 0, 1))
        val actual = threeSum(intArrayOf(-1, 0, 1, 2, -1, -4))
        assertTrue(
            expected.all { expectedTriplet ->
                actual.any { actualTriplet ->
                    actualTriplet.containsAll(expectedTriplet) && actualTriplet.size == expectedTriplet.size
                }
            }
        )
    }
}