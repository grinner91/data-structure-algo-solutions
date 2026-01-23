package neetcode.twopointer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SolutionTwoIntegerSum2TwoPointers {
    fun twoSum(numbers: IntArray, target: Int): IntArray {
        var l = 0
        var r = numbers.lastIndex
        while (l < r) {
            when {
                numbers[l] + numbers[r] == target -> return intArrayOf(l + 1, r + 1)
                numbers[l] + numbers[r] > target -> r--
                else -> l++
            }
        }
        return intArrayOf()
    }
}

class SolutionTwoIntegerSum2BinarySearch {
    fun twoSum(numbers: IntArray, target: Int): IntArray {
        for (i in numbers.indices) {
            var l = i + 1
            var r = numbers.lastIndex
            val n2 = target - numbers[i]
            while (l <= r) {
                val mid = l + (r - l) / 2
                when {
                    numbers[mid] == n2 -> return intArrayOf(i + 1, mid + 1)
                    numbers[mid] < n2 -> l = mid + 1
                    else -> r = mid - 1
                }
            }

        }
        return intArrayOf()
    }
}

class SolutionTwoIntegerSum2HashMap {
    fun twoSum(numbers: IntArray, target: Int): IntArray {
        val map = HashMap<Int, Int>() // k - number, v - index
        numbers.forEachIndexed { j, x ->
            val n2 = target - x
            map[n2]?.let { i ->
                return intArrayOf(i + 1, j + 1)
            }
            map[x] = j
        }
        return intArrayOf()
    }
}

class TwoSumIITest {

    private val impls = listOf(
        SolutionTwoIntegerSum2TwoPointers()::twoSum,
        SolutionTwoIntegerSum2BinarySearch()::twoSum,
        SolutionTwoIntegerSum2HashMap()::twoSum
    )

    private fun assertValid(numbers: IntArray, target: Int, ans: IntArray) {
        assertEquals(2, ans.size, "Answer must contain exactly 2 indices")

        val i = ans[0]
        val j = ans[1]

        assertTrue(i in 1..numbers.size, "Index i must be 1..n")
        assertTrue(j in 1..numbers.size, "Index j must be 1..n")
        assertTrue(i < j, "Expected i < j")

        val sum = numbers[i - 1] + numbers[j - 1]
        assertEquals(target, sum, "numbers[i-1] + numbers[j-1] must equal target")
    }

    @Test
    fun example1() {
        val numbers = intArrayOf(2, 7, 11, 15)
        val target = 9
        val expected = intArrayOf(1, 2)

        impls.forEach { f ->
            val ans = f(numbers, target)
            assertEquals(expected.toList(), ans.toList())
            assertValid(numbers, target, ans)
        }
    }

    @Test
    fun example2() {
        val numbers = intArrayOf(2, 3, 4)
        val target = 6
        val expected = intArrayOf(1, 3)

        impls.forEach { f ->
            val ans = f(numbers, target)
            assertEquals(expected.toList(), ans.toList())
            assertValid(numbers, target, ans)
        }
    }

    @Test
    fun example3() {
        val numbers = intArrayOf(-1, 0)
        val target = -1
        val expected = intArrayOf(1, 2)

        impls.forEach { f ->
            val ans = f(numbers, target)
            assertEquals(expected.toList(), ans.toList())
            assertValid(numbers, target, ans)
        }
    }

    @Test
    fun negativesAndDuplicates() {
        val numbers = intArrayOf(-10, -3, -3, 0, 1, 2, 7, 7, 9)
        val target = 4 // -3 + 7

        impls.forEach { f ->
            val ans = f(numbers, target)
            assertValid(numbers, target, ans)
        }
    }
}
