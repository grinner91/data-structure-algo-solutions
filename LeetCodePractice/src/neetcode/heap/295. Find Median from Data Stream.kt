package neetcode.heap

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.random.Random

class MedianFinder() {
    //max heap - lower half
    private val smallHeap = PriorityQueue<Int>(compareByDescending { it })
    //min heap - upper half
    private val largeHeap = PriorityQueue<Int>()

    fun addNum(num: Int) {
        if (largeHeap.isNotEmpty() && num > largeHeap.peek()) {
            largeHeap.add(num)
        } else {
            smallHeap.add(num)
        }
        //re-balance
        when {
            smallHeap.size > largeHeap.size + 1 -> largeHeap.add(smallHeap.remove())
            largeHeap.size > smallHeap.size + 1 -> smallHeap.add(largeHeap.remove())
        }
    }

    fun findMedian(): Double {
        return when {
            smallHeap.size > largeHeap.size -> smallHeap.peek().toDouble()
            largeHeap.size > smallHeap.size -> largeHeap.peek().toDouble()
            else -> (smallHeap.peek() + largeHeap.peek()) / 2.0
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * var obj = MedianFinder()
 * obj.addNum(num)
 * var param_2 = obj.findMedian()
 */

//chatgpt unit tests
class MedianFinderTest {

    private fun expectedMedian(nums: List<Int>): Double {
        if (nums.isEmpty()) error("expectedMedian called with empty list")
        val sorted = nums.sorted()
        val n = sorted.size
        return if (n % 2 == 1) {
            sorted[n / 2].toDouble()
        } else {
            (sorted[n / 2 - 1].toDouble() + sorted[n / 2].toDouble()) / 2.0
        }
    }

    private fun assertMedianEquals(expected: Double, actual: Double, msg: String? = null) {
        // Double tolerance to handle averages
        assertEquals(expected, actual, 1e-9, msg)
    }

    @Test
    fun `single element`() {
        val mf = MedianFinder()
        mf.addNum(42)
        assertMedianEquals(42.0, mf.findMedian(), "Median of single element should be the element itself")
    }

    @Test
    fun `two elements even count`() {
        val mf = MedianFinder()
        mf.addNum(1)
        mf.addNum(3)
        assertMedianEquals(2.0, mf.findMedian(), "Median of [1,3] should be 2")
    }

    @Test
    fun `odd count after multiple inserts`() {
        val mf = MedianFinder()
        listOf(6, 10, 2, 4, 8).forEach(mf::addNum)
        // Sorted = [2,4,6,8,10] -> median 6
        assertMedianEquals(6.0, mf.findMedian())
    }

    @Test
    fun `even count after multiple inserts non-monotonic`() {
        val mf = MedianFinder()
        listOf(5, 15, 1, 3).forEach(mf::addNum) // Sorted: [1,3,5,15]
        assertMedianEquals(4.0, mf.findMedian(), "Median should average middle two (3 and 5)")
        mf.addNum(8) // Sorted: [1,3,5,8,15]
        assertMedianEquals(5.0, mf.findMedian(), "Median should be middle element for odd count")
    }

    @Test
    fun `handles negatives and duplicates`() {
        val mf = MedianFinder()
        val seq = listOf(-5, -1, -1, -3, -3, -3, 0, 7, 7)
        val seen = mutableListOf<Int>()
        for (x in seq) {
            mf.addNum(x)
            seen += x
            assertMedianEquals(expectedMedian(seen), mf.findMedian(), "Failed on prefix: $seen")
        }
    }

    @Test
    fun `alternating low-high pattern`() {
        val mf = MedianFinder()
        val seen = mutableListOf<Int>()
        val lows = listOf(1, 2, 3, 4, 5)
        val highs = listOf(100, 90, 80, 70, 60)
        for (i in lows.indices) {
            mf.addNum(lows[i]); seen += lows[i]
            assertMedianEquals(expectedMedian(seen), mf.findMedian(), "After adding ${lows[i]}")
            mf.addNum(highs[i]); seen += highs[i]
            assertMedianEquals(expectedMedian(seen), mf.findMedian(), "After adding ${highs[i]}")
        }
    }

    @Test
    fun `large values do not overflow when averaging`() {
        val mf = MedianFinder()
        // Middle average is (Int.MAX_VALUE + Int.MIN_VALUE) / 2.0 -> should not overflow in implementation
        mf.addNum(Int.MAX_VALUE)
        mf.addNum(Int.MIN_VALUE)
        val expected = (Int.MAX_VALUE.toDouble() + Int.MIN_VALUE.toDouble()) / 2.0
        assertMedianEquals(expected, mf.findMedian())
    }

    @Test
    fun `progressive median checks`() {
        val mf = MedianFinder()
        val data = listOf(12, 4, 5, 3, 8, 7) // Common stream example
        val expectedMedians = listOf(12.0, 8.0, 5.0, 4.5, 5.0, 6.0)
        data.forEachIndexed { i, x ->
            mf.addNum(x)
            assertMedianEquals(expectedMedians[i], mf.findMedian(), "Step $i after adding $x")
        }
    }

    @RepeatedTest(3)
    fun `randomized property test vs sorted list oracle`() {
        val mf = MedianFinder()
        val seen = mutableListOf<Int>()
        val rng = Random(42 + Random.nextInt()) // vary seed across repeats
        repeat(1000) {
            val x = rng.nextInt(-1_000_000, 1_000_000)
            mf.addNum(x)
            seen += x
            if (it % 17 == 0) { // sample checks to keep test fast
                val expected = expectedMedian(seen)
                val actual = mf.findMedian()
                assertMedianEquals(expected, actual, "Mismatch at size=${seen.size}")
            }
        }
        // final check on full set
        assertMedianEquals(expectedMedian(seen), mf.findMedian(), "Final median mismatch")
    }
}
