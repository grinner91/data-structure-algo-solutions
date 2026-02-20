package neetcode.stack

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

class DailyTemperaturesBruteForce {
    fun dailyTemperatures(t: IntArray): IntArray {
        val n = t.size
        val ans = IntArray(n)
        for (i in 0 until n) {
            var j = i + 1
            while (j < n && t[j] <= t[i]) j++
            if (j < n) ans[i] = j - i
        }
        return ans
    }
}


class DailyTemperaturesMonotonicStack {
    fun dailyTemperatures(t: IntArray): IntArray {
        val n = t.size
        val ans = IntArray(n)
        val stack = ArrayDeque<Int>()
        for (i in t.indices) {
            while (stack.isNotEmpty() && t[i] > t[stack.last()]) {
                val prev = stack.removeLast()
                ans[i] = i - prev
            }
            stack.addLast(i)
        }
        return ans
    }
}

class DailyTemperaturesTest {

    private val impls = listOf(
        DailyTemperaturesBruteForce()::dailyTemperatures,
//        DailyTemperaturesMonotonicStack::dailyTemperatures,
//        DailyTemperatures.SolutionReverseJump()::dailyTemperatures
    )

    @Test
    fun example1() {
        val input = intArrayOf(73, 74, 75, 71, 69, 72, 76, 73)
        val expected = intArrayOf(1, 1, 4, 2, 1, 1, 0, 0)

        impls.forEach { f ->
            assertArrayEquals(expected, f(input))
        }
    }

    @Test
    fun example2() {
        val input = intArrayOf(30, 40, 50, 60)
        val expected = intArrayOf(1, 1, 1, 0)

        impls.forEach { f ->
            assertArrayEquals(expected, f(input))
        }
    }

    @Test
    fun example3() {
        val input = intArrayOf(30, 60, 90)
        val expected = intArrayOf(1, 1, 0)

        impls.forEach { f ->
            assertArrayEquals(expected, f(input))
        }
    }

    @Test
    fun allDecreasing() {
        val input = intArrayOf(100, 90, 80, 70)
        val expected = intArrayOf(0, 0, 0, 0)

        impls.forEach { f ->
            assertArrayEquals(expected, f(input))
        }
    }

    @Test
    fun allEqual() {
        val input = intArrayOf(50, 50, 50)
        val expected = intArrayOf(0, 0, 0)

        impls.forEach { f ->
            assertArrayEquals(expected, f(input))
        }
    }

    @Test
    fun singleElement() {
        val input = intArrayOf(42)
        val expected = intArrayOf(0)

        impls.forEach { f ->
            assertArrayEquals(expected, f(input))
        }
    }

    @Test
    fun empty() {
        val input = intArrayOf()
        val expected = intArrayOf()

        impls.forEach { f ->
            assertArrayEquals(expected, f(input))
        }
    }
}

