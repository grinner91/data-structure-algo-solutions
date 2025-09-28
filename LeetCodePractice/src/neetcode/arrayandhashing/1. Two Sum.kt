package neetcode.arrayandhashing

import leetcode.twoSum3
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

fun twoSum3(nums: IntArray, target: Int): IntArray {
    val numsMap = mutableMapOf<Int, Int>()
    nums.forEachIndexed { i, n ->
        numsMap[target - n]?.let { j ->
            return intArrayOf(j, i)
        }
        numsMap[n] = i
    }
    return intArrayOf()
}

fun twoSum4(nums: IntArray, target: Int): IntArray {
    val indexedNums = nums.withIndex().sortedBy { it.value } //O(nlogn)
    var left = 0
    var right = indexedNums.lastIndex

    while (left < right) {
        val currentSum = indexedNums[left].value + indexedNums[right].value
        when {
            currentSum == target -> return intArrayOf(
                minOf(indexedNums[left].index, indexedNums[right].index),
                maxOf(indexedNums[left].index, indexedNums[right].index)
            )

            currentSum < target -> left++
            else -> right--
        }
    }
    return intArrayOf()
}

class TwoSum {
    @Test
    fun `should return correct indices when given array`() {
        val expected = intArrayOf(0, 1)
        assertTrue(
            expected.contentEquals(twoSum3(intArrayOf(2, 7, 11, 15), 9))
        )
    }

    @Test
    fun `should return no indices when given array`() {
        val expected = intArrayOf()
        assertTrue(
            expected.contentEquals(twoSum3(intArrayOf(2, 7, 11, 15), 30))
        )
    }
}
