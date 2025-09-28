package neetcode.binarysearch

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/********** solution 1: two pass BS *********************/
fun search1(nums: IntArray, target: Int): Int {
    var pivot = findPivotIndex(nums)
    if (nums[pivot] <= target && target <= nums[nums.lastIndex])
        return searchTarget(nums, target, pivot, nums.lastIndex)
    else
        return searchTarget(nums, target, 0, pivot - 1)
}

private fun findPivotIndex(nums: IntArray): Int {
    var l = nums.indices.first
    var r = nums.indices.last
    while (l < r) {
        val m = l + (r - l) / 2
        if (nums[m] > nums[r])
            l = m + 1 //search space is right part
        else
            r = m //search space is left part
    }
    return l
}

private fun searchTarget(nums: IntArray, target: Int, start: Int, end: Int): Int {
    var l = start
    var r = end
    while (l <= r) {
        val m = l + (r - l) / 2
        if (nums[m] == target)
            return m
        if (nums[m] < target)
            l = m + 1
        else
            r = m - 1
    }
    return -1
}

/********** end solution 1: two pass BS *********************/
/********** solution 2: one pass BS *********************/

fun search(nums: IntArray, target: Int): Int {
    var l = nums.indices.first
    var r = nums.indices.last
    while (l <= r) {
        val m = l + (r - l) / 2
        when {
            nums[m] == target -> return m
            //if left half is sorted
            nums[l] <= nums[m] -> {
                if (target in nums[l]..nums[m])
                    r = m - 1
                else
                    l = m + 1
            }
            //if right half is sorted
            else -> {
                if (target in nums[m]..nums[r])
                    l = m + 1
                else
                    r = m - 1
            }
        }
    }
    return -1
}

/*******************/

class BSearchTest {
    @Test
    fun test1() {
        assertEquals(4, search(intArrayOf(4, 5, 6, 7, 0, 1, 2), 0))
    }

    @Test
    fun test2() {
        assertEquals(-1, search(intArrayOf(4, 5, 6, 7, 0, 1, 2), 3))
    }

    @Test
    fun test3() {
        assertEquals(1, search(intArrayOf(1, 3), 3))
    }
}