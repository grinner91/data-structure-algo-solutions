package neetcode.binarysearch

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun findMin1(nums: IntArray): Int {
    var res = nums.first()
    var l = 0
    var r = nums.lastIndex
    while (l <= r) {
        val mid = l + (r - l) / 2
        res = minOf(res, nums[mid])
        if (nums[l] <= nums[mid]) {
            l = mid + 1
        } else {
            r = mid - 1
        }
    }
    return res
}

fun findMin(nums: IntArray): Int {
    var l = 0
    var r = nums.lastIndex
    while (l < r) {
        val m = l + (r - l) / 2
        if(nums[m] > nums[r])
            l = m + 1
        else
            r = m
    }
    return nums[l]
}

class FindMinTest {
    @Test
    fun test1() {
        assertEquals(1, findMin(intArrayOf(3, 4, 5, 1, 2)))
    }

    @Test
    fun test2() {
        assertEquals(0, findMin(intArrayOf(4,5,0,1,2,3)))
    }
}