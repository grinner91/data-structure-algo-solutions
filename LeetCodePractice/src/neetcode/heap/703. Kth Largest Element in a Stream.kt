package neetcode.heap

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

interface KthLargestStream {
    fun add(`val`: Int): Int
}

class KthLargestMinHeap(
    k: Int,
    nums: IntArray
) : KthLargestStream {
    private val minHeap = PriorityQueue<Int>()
    private val k = k

    init {
        for (x in nums) {
            minHeap.offer(x)
            if (minHeap.size > k) {
                minHeap.poll()
            }
        }
    }

    override fun add(`val`: Int): Int {
        minHeap.offer(`val`)
        if (minHeap.size > k) {
            minHeap.poll()
        }
        return minHeap.peek()
    }
}

class KthLargestElementInAStreamTest {

    private val impls = listOf<(Int, IntArray) -> KthLargestStream>(
        ::KthLargestMinHeap,
    )

    @Test
    fun example1() {
        impls.forEach { factory ->
            val kthLargest = factory(3, intArrayOf(4, 5, 8, 2))

            assertEquals(4, kthLargest.add(3))
            assertEquals(5, kthLargest.add(5))
            assertEquals(5, kthLargest.add(10))
            assertEquals(8, kthLargest.add(9))
            assertEquals(8, kthLargest.add(4))
        }
    }

    @Test
    fun singleInitialValue() {
        impls.forEach { factory ->
            val kthLargest = factory(1, intArrayOf(5))

            assertEquals(5, kthLargest.add(2))
            assertEquals(8, kthLargest.add(8))
            assertEquals(8, kthLargest.add(3))
        }
    }

    @Test
    fun emptyInitialStream() {
        impls.forEach { factory ->
            val kthLargest = factory(1, intArrayOf())

            assertEquals(-3, kthLargest.add(-3))
            assertEquals(-2, kthLargest.add(-2))
            assertEquals(-2, kthLargest.add(-4))
            assertEquals(0, kthLargest.add(0))
            assertEquals(4, kthLargest.add(4))
        }
    }

    @Test
    fun duplicates() {
        impls.forEach { factory ->
            val kthLargest = factory(2, intArrayOf(5, 5, 5))

            assertEquals(5, kthLargest.add(5))
            assertEquals(5, kthLargest.add(6))
            assertEquals(6, kthLargest.add(6))
        }
    }

    @Test
    fun negativeNumbers() {
        impls.forEach { factory ->
            val kthLargest = factory(2, intArrayOf(-10, -7, -11))

            assertEquals(-8, kthLargest.add(-8))
            assertEquals(-7, kthLargest.add(-6))
            assertEquals(-7, kthLargest.add(-7))
        }
    }

    @Test
    fun kEqualsStreamSizeInitially() {
        impls.forEach { factory ->
            val kthLargest = factory(4, intArrayOf(4, 2, 7, 1))

            assertEquals(2, kthLargest.add(3))
            assertEquals(3, kthLargest.add(8))
            assertEquals(4, kthLargest.add(5))
        }
    }
}