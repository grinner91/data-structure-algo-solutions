package neetcode.twopointer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//brut force, O(N^2)
fun trap1(height: IntArray): Int {
    if (height.isEmpty()) return 0
    var result = 0
    for (i in height.indices) {
        var leftMax = height[i]
        for (j in 0 until i) {
            leftMax = maxOf(leftMax, height[j])
        }
        var rightMax = height[i]
        for (j in i until height.size) {
            rightMax = maxOf(rightMax, height[j])
        }
        result += minOf(leftMax, rightMax) - height[i]
    }
    return result
}

//prefix suffix sub-array, O(N)
fun trap2(height: IntArray): Int {
    if (height.isEmpty()) return 0
    //prefix left arr
    val leftMaxArr = IntArray(height.size)
    leftMaxArr[0] = height[0]
    for (i in 1 until height.size) {
        leftMaxArr[i] = maxOf(leftMaxArr[i - 1], height[i])
    }

    //suffix right arr
    val rightMaxArr = IntArray(height.size)
    rightMaxArr[height.size - 1] = height[height.size - 1]
    for (i in (height.size - 2) downTo 0) {
        rightMaxArr[i] = maxOf(rightMaxArr[i + 1], height[i])
    }

    var result = 0
    for (i in height.indices) {
        result += minOf(leftMaxArr[i], rightMaxArr[i]) - height[i]
    }
    return result
}

//two pointer
fun trap(height: IntArray): Int {
    var result = 0
    var leftMax = height.first()
    var rightMax = height.last()
    var left = 0
    var right = height.size - 1
    while (left < right) {
        if (leftMax < rightMax) {
            left++
            leftMax = maxOf(leftMax, height[left])
            result += leftMax - height[left]
        } else {
            right--
            rightMax = maxOf(rightMax, height[right])
            result += rightMax - height[right]
        }
    }
    return result
}

class TrapTests {
    @Test
    fun test1() {
        val input = intArrayOf(0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1)
        val expected = 6
        val actual = trap(input)
        assertEquals(expected, actual)
    }

    @Test
    fun test2() {
        val input = intArrayOf(4, 2, 0, 3, 2, 5)
        val expected = 9
        val actual = trap(input)
        assertEquals(expected, actual)
    }
}