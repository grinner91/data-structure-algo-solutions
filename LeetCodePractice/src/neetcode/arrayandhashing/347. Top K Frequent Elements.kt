package neetcode.arrayandhashing

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*
/*

📊 Complexity Summary Table
Solution Type	Time Complexity	Space Complexity
Sorting 	   O(n + u log u)	O(u)
Min-Heap	   O(n log k)	    O(u + k)
Bucket Sort	   O(n)	            O(n)

* */

class SolutionBucketSort {
    //Bucket sort - TC O(n) , SC O(n)
    fun topKFrequent(nums: IntArray, k: Int): IntArray {
        if (k == 0) return intArrayOf()
        if (nums.isEmpty()) return intArrayOf()

        val freq = nums.asIterable().groupingBy { it }.eachCount()
        val bucket = Array(nums.size + 1) { mutableListOf<Int>() }

        freq.forEach { (n, f) -> bucket[f].add(n) }

        val res = ArrayList<Int>(k)
        for (fr in bucket.lastIndex downTo 1) {
            for (n in bucket[fr]) {
                res.add(n)
                if (res.size == k)
                    return res.toIntArray()
            }
        }
        return intArrayOf()
    }
}

//min-heap
class SolutionMinHeap {
    fun topKFrequent(nums: IntArray, k: Int): IntArray {
        if (k == 0 || nums.isEmpty()) return intArrayOf()

        val pq = PriorityQueue<Pair<Int, Int>>(compareBy { it.second }) //num, freq

        val freq = nums.asIterable().groupingBy { it }.eachCount()

        freq.forEach { (n, f) ->
            pq.add(n to f)
            if (pq.size > k)
                pq.poll()
        }

        return IntArray(k) {
            pq.poll().first
        }.also {
            it.reverse()
        }
    }
}

class SolutionSorting {
    //TC O(nlogn)
    fun topKFrequent(nums: IntArray, k: Int): IntArray {
        return nums.asIterable()
            .groupingBy { it }
            .eachCount()
            .entries
            .sortedByDescending { it.value } //O(nlogn)
            .take(k)
            .map { it.key }
            .toIntArray()
    }
}

private fun assertAsSetEquals(expected: IntArray, actual: IntArray) {
    assertEquals(expected.toSet(), actual.toSet())
}


class TopKFrequentBucketSortTest {

    private val sut = SolutionBucketSort() //Solution()

    // Adapter so test uses Solution() naming you requested
//    private inner class Solution {
//        private val impl = SolutionBucketSort()
//        fun topKFrequent(nums: IntArray, k: Int): IntArray = impl.topKFrequent(nums, k)
//    }

    @Test
    fun `example 1`() {
        val res = sut.topKFrequent(intArrayOf(1, 1, 1, 2, 2, 3), 2)
        assertAsSetEquals(intArrayOf(1, 2), res)
    }

    @Test
    fun `example 2`() {
        val res = sut.topKFrequent(intArrayOf(1), 1)
        assertAsSetEquals(intArrayOf(1), res)
    }

    @Test
    fun `works with negatives`() {
        val res = sut.topKFrequent(intArrayOf(-1, -1, -2, -2, -2, 3), 2)
        assertAsSetEquals(intArrayOf(-2, -1), res)
    }

    @Test
    fun `k equals number of unique`() {
        val res = sut.topKFrequent(intArrayOf(4, 4, 5, 6), 3)
        assertAsSetEquals(intArrayOf(4, 5, 6), res)
    }

    @Test
    fun `all same`() {
        val res = sut.topKFrequent(intArrayOf(7, 7, 7, 7), 2)
        assertAsSetEquals(intArrayOf(7), res)
    }
}
