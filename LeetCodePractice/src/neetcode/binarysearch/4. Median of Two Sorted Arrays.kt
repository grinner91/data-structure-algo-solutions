package neetcode.binarysearch

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MedianOfTwoSortedArraysBruteForce {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val merged = (nums1 + nums2).sorted()
        val len = merged.size
        return if (len % 2 == 0) {
            (merged[len / 2 - 1] + merged[len / 2]).toDouble() / 2.0
        } else {
            merged[len / 2].toDouble()
        }
    }
}

class MedianOfTwoSortedArraysTwoPointer {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val l1 = nums1.size
        val l2 = nums2.size
        val total = l1 + l2
        val mid1 = (total - 1) / 2
        val mid2 = total / 2
        var i = 0
        var j = 0
        var idx = 0
        var v1 = 0
        var v2 = 0
        while (idx <= mid2) {
            val temp = when {
                i < l1 && (j >= l2 || nums1[i] <= nums2[j]) -> nums1[i++]
                else -> nums2[j++]
            }
            if (idx == mid1) v1 = temp
            if (idx == mid2) v2 = temp
            idx++
        }
        return if ((total and 1) == 1) v2.toDouble()
        else (v1 + v2).toDouble() / 2.0
    }
}

class MedianOfTwoSortedArraysTest {

    private val impls = listOf(
        //     MedianOfTwoSortedArraysBruteForce()::findMedianSortedArrays,
        MedianOfTwoSortedArraysTwoPointer()::findMedianSortedArrays,
//        MedianOfTwoSortedArrays.SolutionKth()::findMedianSortedArrays,
    )

    @Test
    fun example1() {
        val a = intArrayOf(1, 3)
        val b = intArrayOf(2)
        impls.forEach { f -> assertEquals(2.0, f(a, b), 1e-9) }
    }

    @Test
    fun example2() {
        val a = intArrayOf(1, 2)
        val b = intArrayOf(3, 4)
        impls.forEach { f -> assertEquals(2.5, f(a, b), 1e-9) }
    }

    @Test
    fun oneEmpty() {
        val a = intArrayOf()
        val b = intArrayOf(5)
        impls.forEach { f -> assertEquals(5.0, f(a, b), 1e-9) }
    }

    @Test
    fun evenTotal_withDuplicates() {
        val a = intArrayOf(1, 2, 2)
        val b = intArrayOf(2, 2, 3)
        impls.forEach { f -> assertEquals(2.0, f(a, b), 1e-9) }
    }

    @Test
    fun negatives() {
        val a = intArrayOf(-5, -3, -1)
        val b = intArrayOf(-2, 0, 2)
        impls.forEach { f -> assertEquals(-1.5, f(a, b), 1e-9) }
    }

    @Test
    fun highlyUnbalanced() {
        val a = intArrayOf(1)
        val b = intArrayOf(2, 3, 4, 5, 6, 7, 8, 9)
        impls.forEach { f -> assertEquals(5.0, f(a, b), 1e-9) }
    }
}