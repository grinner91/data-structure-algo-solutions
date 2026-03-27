package neetcode.heap

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class KthLargestElementInAnArrayMinHeap {
    fun findKthLargest(nums: IntArray, k: Int): Int {
        val minHeap = PriorityQueue<Int>()
        nums.forEach {
            minHeap.offer(it)
            if (minHeap.size > k) {
                minHeap.poll()
            }
        }
        return minHeap.peek()
    }
}

class KthLargestElementInAnArrayMaxHeap {
    fun findKthLargest(nums: IntArray, k: Int): Int {
        val maxHeap = PriorityQueue<Int>(compareByDescending { it })
        nums.forEach { maxHeap.offer(it) }
        repeat(k - 1) {
            maxHeap.poll()
        }
        return maxHeap.peek()
    }
}

class KthLargestElementInAnArrayTest {

    private val impls = listOf(
        KthLargestElementInAnArrayMinHeap()::findKthLargest,
    )

    @Test
    fun example1() {
        impls.forEach { findKthLargest ->
            assertEquals(5, findKthLargest(intArrayOf(3, 2, 1, 5, 6, 4), 2))
        }
    }

    @Test
    fun example2() {
        impls.forEach { findKthLargest ->
            assertEquals(4, findKthLargest(intArrayOf(3, 2, 3, 1, 2, 4, 5, 5, 6), 4))
        }
    }

    @Test
    fun singleElement() {
        impls.forEach { findKthLargest ->
            assertEquals(1, findKthLargest(intArrayOf(1), 1))
        }
    }

    @Test
    fun twoElementsK1() {
        impls.forEach { findKthLargest ->
            assertEquals(2, findKthLargest(intArrayOf(1, 2), 1))
        }
    }

    @Test
    fun twoElementsK2() {
        impls.forEach { findKthLargest ->
            assertEquals(1, findKthLargest(intArrayOf(1, 2), 2))
        }
    }

    @Test
    fun allSameValues() {
        impls.forEach { findKthLargest ->
            assertEquals(7, findKthLargest(intArrayOf(7, 7, 7, 7, 7), 3))
        }
    }

    @Test
    fun withNegativeNumbers() {
        impls.forEach { findKthLargest ->
            assertEquals(-2, findKthLargest(intArrayOf(-1, -2, -3, -4, -5), 2))
        }
    }

    @Test
    fun mixedPositiveAndNegative() {
        impls.forEach { findKthLargest ->
            assertEquals(0, findKthLargest(intArrayOf(-10, 4, 0, 7, -3, 2), 3))
        }
    }

    @Test
    fun duplicatesAffectRanking() {
        impls.forEach { findKthLargest ->
            assertEquals(4, findKthLargest(intArrayOf(5, 4, 4, 3, 2), 2))
        }
    }

    @Test
    fun kEqualsArraySize() {
        impls.forEach { findKthLargest ->
            assertEquals(1, findKthLargest(intArrayOf(9, 3, 7, 1, 5), 5))
        }
    }

    @Test
    fun largerCase() {
        impls.forEach { findKthLargest ->
            assertEquals(8, findKthLargest(intArrayOf(9, 8, 7, 6, 5, 4, 3, 2, 1), 2))
        }
    }
}