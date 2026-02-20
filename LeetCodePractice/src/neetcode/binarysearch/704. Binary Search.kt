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

class BinarySearch704UpperBound {
    /*
     //first index where value greater than the target appears
     Why l > 0?
     Because upperBound can return 0 when all elements are greater than target.
     In that case l - 1 would be -1,
     which is invalid. So we must guard against negative indexing.
     */
    fun search(nums: IntArray, target: Int): Int {
        var l = 0
        var r = nums.size //one past lastIndex
        while (l < r) {
            val mid = l + (r - l) / 2
            when {
                // now equality moves RIGHT, Upper Bound pushes equality to RIGHT
                //<= target → move left boundary right
                nums[mid] <= target -> l = mid + 1
                else -> r = mid
            }
        }
        return if (l > 0 && nums[l - 1] == target) l - 1
        else -1
    }
}

class BinarySearch704LowerBound {
    /*
    - first index where a value is greater than or equal the target
    Lower Bound  →  if (x >= target)  r = mid
    Upper Bound  →  if (x >  target)  r = mid
    Lower bound allows equality, So equality stays on the LEFT side.
    Upper bound rejects equality

    THE REAL DIFFERENCE
    Lower Bound keeps equality on LEFT:
    >= target → shrink right
    Upper Bound pushes equality to RIGHT:
    <= target → move left boundary right
    * */
    fun search(nums: IntArray, target: Int): Int {
        var l = 0
        var r = nums.size
        while (l < r) {
            val mid = l + (r - l) / 2
            when {
                nums[mid] < target -> l = mid + 1
                else -> r = mid // >= target → shrink right, now equality moves LEFT
            }
        }
        return if (r < nums.size && nums[l] == target) l
        else -1
    }
}


class BinarySearch704Test {

    private val impls = listOf(
        // BinarySearch704Iterative()::search,
        // BinarySearch704Recursion()::search,
//        BinarySearch704UpperBound()::search,
        BinarySearch704LowerBound()::search,
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
