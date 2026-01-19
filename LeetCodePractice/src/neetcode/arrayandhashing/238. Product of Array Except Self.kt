package neetcode.arrayandhashing

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

class SolutionPrefixSuffix {
    //two pass left scan and right scan
    fun productExceptSelf(nums: IntArray): IntArray {
        val left = IntArray(nums.size)
        left[0] = 1
        for (i in 1 until nums.size) {
            left[i] = left[i - 1] * nums[i - 1]

        }
        val right = IntArray(nums.size)
        right[nums.size - 1] = 1
        for (i in nums.size - 2 downTo 0) {
            right[i] = right[i + 1] * nums[i + 1]
        }

        val prod = IntArray(nums.size)
        for (i in nums.indices) {
            prod[i] = left[i] * right[i]
        }
        return prod
    }
}

class SolutionPrefixSuffixOptimal {
    fun productExceptSelf(nums: IntArray): IntArray {
        val prod = IntArray(nums.size)
        val n = nums.size
        var prefix = 1
        for (i in 0 until n) {
            prod[i] = prefix
            prefix *= nums[i]
        }
        var postfix = 1
        for (i in n - 1 downTo 0) {
            prod[i] *= postfix //note prod is prod * postfix
            postfix *= nums[i]
        }
        return prod
    }
}

class ProductExceptSelfTest {

    private val impls = listOf(
        SolutionPrefixSuffix()::productExceptSelf,
        SolutionPrefixSuffixOptimal()::productExceptSelf
    )

    @Test
    fun example1() {
        val nums = intArrayOf(1, 2, 3, 4)
        val expected = intArrayOf(24, 12, 8, 6)
        impls.forEach { f -> assertArrayEquals(expected, f(nums)) }
    }

    @Test
    fun example2_withZero() {
        val nums = intArrayOf(-1, 1, 0, -3, 3)
        val expected = intArrayOf(0, 0, 9, 0, 0)
        impls.forEach { f -> assertArrayEquals(expected, f(nums)) }
    }

    @Test
    fun singleZero() {
        val nums = intArrayOf(2, 0, 4)
        val expected = intArrayOf(0, 8, 0)
        impls.forEach { f -> assertArrayEquals(expected, f(nums)) }
    }

    @Test
    fun twoZeros() {
        val nums = intArrayOf(2, 0, 4, 0)
        val expected = intArrayOf(0, 0, 0, 0)
        impls.forEach { f -> assertArrayEquals(expected, f(nums)) }
    }

    @Test
    fun negatives() {
        val nums = intArrayOf(-1, -2, -3, -4)
        val expected = intArrayOf(-24, -12, -8, -6)
        impls.forEach { f -> assertArrayEquals(expected, f(nums)) }
    }

    @Test
    fun mixed() {
        val nums = intArrayOf(5, 1, 10, 2)
        val expected = intArrayOf(20, 100, 10, 50)
        impls.forEach { f -> assertArrayEquals(expected, f(nums)) }
    }
}



