package neetcode.twopointer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Solution3SumTwoPointers {
    fun threeSum(nums: IntArray): List<List<Int>> {
        nums.sort() //O(nlogn)
        val res = mutableListOf<List<Int>>()
        val n = nums.size
        for (i in nums.indices) {
            if (i > 0 && nums[i] == nums[i - 1]) continue //skip duplicates
            var l = i + 1
            var r = nums.lastIndex
            while (l < r) {
                val sum3 = nums[i] + nums[l] + nums[r]
                when {
                    sum3 == 0 -> {
                        res.add(listOf(nums[i], nums[l], nums[r]))
                        l++
                        r--
                        while (l < n && nums[l] == nums[l - 1]) l++ //skip duplicates
                        while (r > 0 && nums[r] == nums[r + 1]) r-- //skip duplicates
                    }

                    sum3 > 0 -> r--
                    else -> l++
                }
            }
        }
        return res
    }
}

class Solution3SumMapFrequency {
    fun threeSum(nums: IntArray): List<List<Int>> {
        nums.sort()
        val res = mutableListOf<List<Int>>()
        val freq = nums.asIterable().groupingBy { it }.eachCount().toMutableMap()
        fun dec(x: Int) {
            freq[x] = (freq[x] ?: 0) - 1
        }

        fun inc(x: Int) {
            freq[x] = (freq[x] ?: 0) + 1
        }

        fun isAvailable(x: Int) = (freq[x] ?: 0) > 0

        for (i in nums.indices) {
            val a = nums[i]
            if (i > 0 && a == nums[i - 1]) continue //skip duplicates
            dec(a) //remove one a
            for (j in i + 1 until nums.size) {
                val b = nums[j]
                if (j > i + 1 && b == nums[j - 1]) continue //skip duplicates
                dec(b) //remove one b
                val c = -(a + b)
                if (isAvailable(c)) {
                    res.add(listOf(a, b, c))
                }
                inc(b) //restore count
            }
            inc(a) //restore count
        }
        return res
    }
}

class ThreeSumTest {

    private val impls = listOf(
        Solution3SumTwoPointers()::threeSum,
        Solution3SumMapFrequency()::threeSum
    )

    @Test
    fun exampleCase() {
        val nums = intArrayOf(-1, 0, 1, 2, -1, -4)
        val expected = setOf(
            listOf(-1, -1, 2),
            listOf(-1, 0, 1)
        )

        impls.forEach { threeSum ->
            val result = threeSum(nums).map { it.sorted() }.toSet()
            assertEquals(expected, result)
        }
    }

    @Test
    fun allZeros() {
        val nums = intArrayOf(0, 0, 0, 0)
        val expected = setOf(listOf(0, 0, 0))

        impls.forEach { threeSum ->
            val result = threeSum(nums).map { it.sorted() }.toSet()
            assertEquals(expected, result)
        }
    }

    @Test
    fun noSolution() {
        val nums = intArrayOf(1, 2, -2, -1)

        impls.forEach { threeSum ->
            val result = threeSum(nums)
            assertEquals(emptyList<List<Int>>(), result)
        }
    }

    @Test
    fun emptyArray() {
        val nums = intArrayOf()

        impls.forEach { threeSum ->
            val result = threeSum(nums)
            assertEquals(emptyList<List<Int>>(), result)
        }
    }
}
