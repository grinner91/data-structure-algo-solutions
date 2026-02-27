package neetcode.binarysearch

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FindMinimumInRotatedSortedArrayBruteForce {
    fun findMin(nums: IntArray): Int {
        var min = nums[0]
        for (x in nums) {
            if(x < min) {
                min = x
            }
        }
        return min
    }
}

class FindMinimumInRotatedSortedArrayBinarySearchLowerBound {
    fun findMin(nums: IntArray): Int {
        var l = 0
        var r = nums.lastIndex
        while (l < r) {
            val mid = l + (r - l) / 2
            when {
                nums[mid] > nums[r] -> l = mid + 1
                else -> r = mid // <= , moves lower bound / Left
            }
        }
        return nums[l]
    }
}
class FindMinimumInRotatedSortedArrayTest {

    private val impls = listOf(
      //  FindMinimumInRotatedSortedArrayBruteForce()::findMin,
        FindMinimumInRotatedSortedArrayBinarySearchLowerBound()::findMin,
//        SolutionRecursive()::findMin,
//        SolutionLinearScan()::findMin,
    )

    @Test
    fun example1() {
        val nums = intArrayOf(3, 4, 5, 1, 2)
        impls.forEach { f -> assertEquals(1, f(nums)) }
    }

    @Test
    fun example2() {
        val nums = intArrayOf(4, 5, 6, 7, 0, 1, 2)
        impls.forEach { f -> assertEquals(0, f(nums)) }
    }

    @Test
    fun example3_singleElement() {
        val nums = intArrayOf(11)
        impls.forEach { f -> assertEquals(11, f(nums)) }
    }

    @Test
    fun notRotated_sortedArray() {
        val nums = intArrayOf(1, 2, 3, 4, 5)
        impls.forEach { f -> assertEquals(1, f(nums)) }
    }

    @Test
    fun rotated_nearEnd() {
        val nums = intArrayOf(2, 3, 4, 5, 1)
        impls.forEach { f -> assertEquals(1, f(nums)) }
    }

    @Test
    fun rotated_nearStart() {
        val nums = intArrayOf(5, 1, 2, 3, 4)
        impls.forEach { f -> assertEquals(1, f(nums)) }
    }

    @Test
    fun twoElements_rotated() {
        val nums = intArrayOf(2, 1)
        impls.forEach { f -> assertEquals(1, f(nums)) }
    }

    @Test
    fun twoElements_notRotated() {
        val nums = intArrayOf(1, 2)
        impls.forEach { f -> assertEquals(1, f(nums)) }
    }

    @Test
    fun negativeValues_rotated() {
        val nums = intArrayOf(0, 2, 5, -10, -3)
        impls.forEach { f -> assertEquals(-10, f(nums)) }
    }
}
