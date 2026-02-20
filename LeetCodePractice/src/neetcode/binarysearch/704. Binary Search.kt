package neetcode.binarysearch


// Unit tests (JUnit 5)
// Uses your preferred function-reference list style.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BinarySearch704Iterative {
    fun search(nums: IntArray, target: Int): Int {
        var l = 0
        var r = nums.lastIndex
        while (l <= r) {
            val mid = l + (r - l) / 2
            val cur = nums[mid]
            when {
                cur == target -> return mid
                cur > target -> r = mid - 1
                else -> l = mid + 1
            }
        }
        return -1
    }
}

class BinarySearch704Recursion {
    fun search(nums: IntArray, target: Int): Int {
        fun binarySearch(l: Int, r: Int): Int {
            if (l > r) return -1
            val mid = l + (r - l) / 2
            val x = nums[mid]
            return when {
                x == target -> mid
                x > target -> binarySearch(l, mid - 1)
                else -> binarySearch(mid + 1, r)
            }
        }
        return binarySearch(0, nums.lastIndex)
    }

}


class BinarySearch704Test {

    private val impls = listOf(
        // BinarySearch704Iterative()::search,
        BinarySearch704Recursion()::search,
    )

    @Test
    fun example_found() {
        val nums = intArrayOf(-1, 0, 3, 5, 9, 12)
        val target = 9
        impls.forEach { f ->
            assertEquals(4, f(nums, target))
        }
    }

    @Test
    fun example_notFound() {
        val nums = intArrayOf(-1, 0, 3, 5, 9, 12)
        val target = 2
        impls.forEach { f ->
            assertEquals(-1, f(nums, target))
        }
    }

    @Test
    fun emptyArray() {
        val nums = intArrayOf()
        impls.forEach { f ->
            assertEquals(-1, f(nums, 10))
        }
    }

    @Test
    fun singleElement_found_and_notFound() {
        val nums = intArrayOf(7)
        impls.forEach { f ->
            assertEquals(0, f(nums, 7))
            assertEquals(-1, f(nums, 6))
        }
    }

    @Test
    fun boundaries_first_and_last() {
        val nums = intArrayOf(1, 3, 5, 7, 9)
        impls.forEach { f ->
            assertEquals(0, f(nums, 1))
            assertEquals(4, f(nums, 9))
        }
    }

    @Test
    fun negatives_and_largeRange() {
        val nums = intArrayOf(-10, -3, 0, 4, 8, 100, 250)
        impls.forEach { f ->
            assertEquals(1, f(nums, -3))
            assertEquals(6, f(nums, 250))
            assertEquals(-1, f(nums, 11))
        }
    }
}
