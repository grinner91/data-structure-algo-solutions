package leetcode

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

fun twoSum3(nums: IntArray, target: Int): IntArray {
    val idxMap = mutableMapOf<Int, Int>()
    nums.forEachIndexed { i, x ->
        if (idxMap.containsKey(target - x)) {
            return intArrayOf(idxMap[target - x]!!, i)
        }
        idxMap[x] = i
    }
    return intArrayOf()
}

class TwoSumTests {
    @Test
    fun `should return correct when given array`() {
        val expected = intArrayOf(0,1)
        assertTrue(
           expected.contentEquals(  twoSum3(intArrayOf(2, 7, 11, 15), 9))
        )
    }
}