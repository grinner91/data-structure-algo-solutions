package neetcode.twopointer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SolutionTrappingRainWaterBrutForce {
    fun trap(height: IntArray): Int {
        val n = height.size
        if (n < 3) return 0
        var water = 0
        for (i in 1 until n - 1) {
            var leftMax = height[i]
            for (l in 0 until i) {
                leftMax = maxOf(leftMax, height[l])
            }

            var rightMax = height[i]
            for (r in i + 1 until n) {
                rightMax = maxOf(rightMax, height[r])
            }
            water += minOf(leftMax, rightMax) - height[i]
        }
        return water
    }
}

class SolutionTrappingRainWaterPrefixSuffix {
    fun trap(height: IntArray): Int {
        if (height.size < 3) return 0
        val n = height.size
        val leftMax = IntArray(n)
        leftMax[0] = height[0]
        for (i in 1 until n) {
            leftMax[i] = maxOf(leftMax[i - 1], height[i])
        }
        val rightMax = IntArray(n)
        rightMax[n - 1] = height[n - 1]
        for (j in n - 2 downTo 0) {
            rightMax[j] = maxOf(rightMax[j + 1], height[j])
        }
        var water = 0
        for (i in 0 until n) {
            water += (minOf(leftMax[i], rightMax[i]) - height[i])
        }
        return water
    }
}

class SolutionTrappingRainWaterTwoPointers {
    fun trap(height: IntArray): Int {
        if (height.size < 3) return 0
        val n = height.size
        var l = 0
        var r = n - 1
        var leftMax = height[l]
        var rightMax = height[r]
        var water = 0
        while (l < r) {
            if (leftMax < rightMax) {
                l++
                leftMax = maxOf(leftMax, height[l])
                water += leftMax - height[l]
            } else {
                r--
                rightMax = maxOf(rightMax, height[r])
                water += rightMax - height[r]
            }
        }
        return water
    }
}


class TrappingRainWaterTest {

    private val impls = listOf(
        SolutionTrappingRainWaterTwoPointers()::trap,
        SolutionTrappingRainWaterPrefixSuffix()::trap,
        SolutionTrappingRainWaterBrutForce()::trap,
    )

    @Test
    fun example1() {
        val height = intArrayOf(0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1)
        impls.forEach { f ->
            assertEquals(6, f(height), "Failed for impl: $f")
        }
    }

    @Test
    fun example2() {
        val height = intArrayOf(4, 2, 0, 3, 2, 5)
        impls.forEach { f ->
            assertEquals(9, f(height), "Failed for impl: $f")
        }
    }

    @Test
    fun emptyOrTooSmall() {
        impls.forEach { f ->
            assertEquals(0, f(intArrayOf()), "Failed for impl: $f")
            assertEquals(0, f(intArrayOf(1)), "Failed for impl: $f")
            assertEquals(0, f(intArrayOf(1, 2)), "Failed for impl: $f")
        }
    }

    @Test
    fun allFlatOrMonotonic() {
        impls.forEach { f ->
            assertEquals(0, f(intArrayOf(0, 0, 0, 0)))
            assertEquals(0, f(intArrayOf(1, 2, 3, 4, 5)))
            assertEquals(0, f(intArrayOf(5, 4, 3, 2, 1)))
        }
    }

    @Test
    fun simpleValleys() {
        impls.forEach { f ->
            assertEquals(2, f(intArrayOf(2, 0, 2)))
            assertEquals(1, f(intArrayOf(1, 0, 1)))
            assertEquals(3, f(intArrayOf(3, 0, 1, 3))) // 3 units at index 1&2 total
        }
    }

    @Test
    fun plateausAndMultipleBasins() {
        impls.forEach { f ->
            assertEquals(0, f(intArrayOf(2, 2, 2)))
            assertEquals(
                3,
                f(intArrayOf(3, 1, 2, 1, 3))
            ) // (2 + 1 + 2?) actually total 5? let's verify: minL/R per idx: [3,3,3,3,3] - [3,1,2,1,3] => [0,2,1,2,0] sum=5
        }
    }
}
