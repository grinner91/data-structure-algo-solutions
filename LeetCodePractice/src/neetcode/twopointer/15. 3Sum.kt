package neetcode.twopointer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun threeSum(nums: IntArray): List<List<Int>> {
    nums.sort() //O(N.logN)
    val result = mutableListOf<List<Int>>()
    for (i in nums.indices - 2) {
        //edge cases
        if (nums[i] > 0) return result
        if (i > 0 && nums[i] == nums[i - 1]) continue
        //two pointer
        var left = i + 1
        var right = nums.lastIndex
        while (left < right) {
            val currentSum = nums[i] + nums[left] + nums[right]
            when {
                currentSum > 0 -> right--
                currentSum < 0 -> left++
                else -> {
                    result.add(listOf(nums[i], nums[left], nums[right]))
                    left++
                    right--
                    //skip duplicates from left side
                    while (left < right && nums[left] == nums[left - 1]) left++
                    //skip duplicates right side
                    while (left < right && nums[right] == nums[right + 1]) right--
                }
            }
        }
    }
    return result
}

class TestThreeSum() {

    // Normalize: sort inside each triplet, then sort the list of triplets
    private fun normalize(triplets: List<List<Int>>): List<List<Int>> =
        triplets.map { it.sorted() }
            .sortedWith(compareBy({ it[0] }, { it[1] }, { it[2] }))

    @Test
    fun test1() {
        val nums = intArrayOf(-1, 0, 1, 2, -1, -4)
        val expected = listOf(listOf(-1, -1, 2), listOf(-1, 0, 1))
        val actual = threeSum(nums)
        assertEquals(normalize(expected), normalize(actual))
    }
}