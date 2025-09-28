package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun twoSum(numbers: IntArray, target: Int): IntArray {
    var left = numbers.indices.first
    var right = numbers.indices.last
    while (left < right) {
        val currentSum = numbers[left] + numbers[right]
        if (currentSum == target) return intArrayOf(left + 1, right + 1)
        else if (currentSum < target) left++
        else right--
    }
    return intArrayOf()
}

fun twoSum2(numbers: IntArray, target: Int): IntArray {
    var result = intArrayOf()
    val numIdxMap = mutableMapOf<Int, Int>()
    numIdxMap.forEach { (v, i) ->
        if (numIdxMap.containsKey(target - v)) {
            result = intArrayOf(numIdxMap[target - v]!! + 1, i + 1)
            return@forEach
        } else {
            numIdxMap[v] = i
        }
    }
    return result
}

class TwoSumTest {
    @Test
    fun `should return two indices_1_2`() {
        assertEquals(
            intArrayOf(1, 2).joinToString(),
            twoSum(intArrayOf(2, 7, 11, 15), 9).joinToString()
        )
    }

    @Test
    fun `should return two indices_1_2_map`() {
        assertEquals(
            intArrayOf(1, 2).joinToString(),
            twoSum2(intArrayOf(2, 7, 11, 15), 9).joinToString()
        )
    }
}