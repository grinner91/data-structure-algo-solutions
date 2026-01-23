package neetcode.twopointer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.max
import kotlin.math.min

class ContainWithMostWaterTwoPointers {
    fun maxArea(height: IntArray): Int {
        var res = 0
        var l = 0
        var r = height.lastIndex
        while (l < r) {
            val cur = min(height[l], height[r]) * (r - l)
            res = max(res, cur)
            when {
                height[l] < height[r] -> l++
                else -> r--
            }
        }
        return res
    }
}


class ContainerWithMostWaterTest {

    private val impls = listOf(
        ContainWithMostWaterTwoPointers()::maxArea,
    )

    @Test
    fun example1() {
        val height = intArrayOf(1, 8, 6, 2, 5, 4, 8, 3, 7)
        impls.forEach { f ->
            assertEquals(49, f(height))
        }
    }

    @Test
    fun example2() {
        val height = intArrayOf(1, 1)
        impls.forEach { f ->
            assertEquals(1, f(height))
        }
    }

    @Test
    fun increasingHeights() {
        val height = intArrayOf(1, 2, 3, 4, 5)
        // best: between 2 (idx1) and 5 (idx4): width 3 * min(2,5)=2 => 6
        impls.forEach { f ->
            assertEquals(6, f(height))
        }
    }

    @Test
    fun decreasingHeights() {
        val height = intArrayOf(5, 4, 3, 2, 1)
        // best: between 5 (idx0) and 2 (idx3): width 3 * min(5,2)=2 => 6
        impls.forEach { f ->
            assertEquals(6, f(height))
        }
    }

    @Test
    fun allEqual() {
        val height = intArrayOf(4, 4, 4, 4)
        // best: endpoints: width 3 * 4 = 12
        impls.forEach { f ->
            assertEquals(12, f(height))
        }
    }

    @Test
    fun containsZeros() {
        val height = intArrayOf(0, 2, 0, 3, 0)
        // best: between 2 (idx1) and 3 (idx3): width 2 * min(2,3)=2 => 4
        impls.forEach { f ->
            assertEquals(4, f(height))
        }
    }

    @Test
    fun singlePeak() {
        val height = intArrayOf(1, 2, 10, 2, 1)
        // best: between idx1 (2) and idx3 (2): width 2 * 2 = 4
        // also endpoints: width 4 * 1 = 4
        impls.forEach { f ->
            assertEquals(4, f(height))
        }
    }
}
