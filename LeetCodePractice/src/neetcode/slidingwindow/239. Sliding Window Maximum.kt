package neetcode.slidingwindow

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test


class SlidingWindowMaximumBruteForce {
    fun maxSlidingWindow(nums: IntArray, k: Int): IntArray {
        require(k >= 1)
        if (nums.isEmpty()) return intArrayOf()
        require(k <= nums.size)

        val res = mutableListOf<Int>()
        for (i in 0 until (nums.size - k + 1)) {
            var curMax = nums[i]
            for (j in i until i + k) {
                curMax = maxOf(curMax, nums[j])
            }
            res.add(curMax)
        }
        return res.toIntArray()
    }
}

class SlidingWindowMaximumDeque {
    fun maxSlidingWindow(nums: IntArray, k: Int): IntArray {
        if (nums.isEmpty() || k > nums.size) return intArrayOf()
        val n = nums.size
        val deq = ArrayDeque<Int>()
        val res = mutableListOf<Int>()
        for (r in 0 until n) {
            val l = r - k + 1
            //remove left element out of window
            if (deq.isNotEmpty() && deq.first() < l) {
                deq.removeFirst()
            }
            //remove smaller or equals from right of the window
            while (deq.isNotEmpty() && nums[deq.last()] < nums[r]) {
                deq.removeLast()
            }
            //add right index
            deq.addLast(r)
            //add max of window
            if (r >= k - 1) {
                res.add(nums[deq.first()])
            }
        }
        return res.toIntArray()
    }
}

class SlidingWindowMaximumTest {

    private val impls: List<(IntArray, Int) -> IntArray> = listOf(
        //SlidingWindowMaximumBruteForce()::maxSlidingWindow,
        SlidingWindowMaximumDeque()::maxSlidingWindow,
//        SolutionMonotonicDeque()::maxSlidingWindow
    )

    @Test
    fun example1() {
        val nums = intArrayOf(1, 3, -1, -3, 5, 3, 6, 7)
        val k = 3
        val expected = intArrayOf(3, 3, 5, 5, 6, 7)

        impls.forEach { f ->
            assertArrayEquals(expected, f(nums, k))
        }
    }

    @Test
    fun example2_singleElement() {
        val nums = intArrayOf(1)
        val k = 1
        val expected = intArrayOf(1)

        impls.forEach { f ->
            assertArrayEquals(expected, f(nums, k))
        }
    }

    @Test
    fun kEqualsArrayLength() {
        val nums = intArrayOf(2, 1, 7, 3)
        val k = 4
        val expected = intArrayOf(7)

        impls.forEach { f ->
            assertArrayEquals(expected, f(nums, k))
        }
    }

    @Test
    fun increasingArray() {
        val nums = intArrayOf(1, 2, 3, 4, 5)
        val k = 2
        val expected = intArrayOf(2, 3, 4, 5)

        impls.forEach { f ->
            assertArrayEquals(expected, f(nums, k))
        }
    }

    @Test
    fun decreasingArray() {
        val nums = intArrayOf(5, 4, 3, 2, 1)
        val k = 3
        val expected = intArrayOf(5, 4, 3)

        impls.forEach { f ->
            assertArrayEquals(expected, f(nums, k))
        }
    }

    @Test
    fun duplicates() {
        val nums = intArrayOf(4, 4, 4, 4)
        val k = 2
        val expected = intArrayOf(4, 4, 4)

        impls.forEach { f ->
            assertArrayEquals(expected, f(nums, k))
        }
    }

    @Test
    fun negatives() {
        val nums = intArrayOf(-7, -8, 7, 5, 7, 1, 6, 0)
        val k = 4
        val expected = intArrayOf(7, 7, 7, 7, 7)

        impls.forEach { f ->
            assertArrayEquals(expected, f(nums, k))
        }
    }
}
