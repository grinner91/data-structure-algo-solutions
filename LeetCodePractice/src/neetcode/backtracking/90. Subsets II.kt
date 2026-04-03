package neetcode.backtracking

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//Sort first, then at the same recursion level skip equal values.
//O(n · 2^n)
//O(n · 2^n)
class SubsetsIIBacktrackLoop {
    fun subsetsWithDup(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val subset = mutableListOf<Int>()

        nums.sort()

        fun backtrack(start: Int) {
            result.add(subset.toList())
            for (i in start until nums.size) {
                //This does NOT remove duplicates globally
                //It only prevents duplicates at the SAME recursion level
                if (i > start && nums[i - 1] == nums[i]) continue

                subset.add(nums[i])
                backtrack(i + 1)
                subset.removeLast()
            }
        }
        backtrack(0)
        return result
    }
}

class SubsetsIiTest {

    private val impls = listOf(
        SubsetsIIBacktrackLoop()::subsetsWithDup
    )

    @Test
    fun example1() {
        val nums = intArrayOf(1, 2, 2)
        val expected = listOf(
            emptyList(),
            listOf(1),
            listOf(2),
            listOf(1, 2),
            listOf(2, 2),
            listOf(1, 2, 2),
        )

        impls.forEach { f ->
            assertEquals(
                normalize(expected),
                normalize(f(nums.clone()))
            )
        }
    }

    @Test
    fun example2() {
        val nums = intArrayOf(0)
        val expected = listOf(
            emptyList(),
            listOf(0),
        )

        impls.forEach { f ->
            assertEquals(
                normalize(expected),
                normalize(f(nums.clone()))
            )
        }
    }

    @Test
    fun allDuplicates() {
        val nums = intArrayOf(2, 2, 2)
        val expected = listOf(
            emptyList(),
            listOf(2),
            listOf(2, 2),
            listOf(2, 2, 2),
        )

        impls.forEach { f ->
            assertEquals(
                normalize(expected),
                normalize(f(nums.clone()))
            )
        }
    }

    @Test
    fun noDuplicates() {
        val nums = intArrayOf(1, 2, 3)
        val expected = listOf(
            emptyList(),
            listOf(1),
            listOf(2),
            listOf(3),
            listOf(1, 2),
            listOf(1, 3),
            listOf(2, 3),
            listOf(1, 2, 3),
        )

        impls.forEach { f ->
            assertEquals(
                normalize(expected),
                normalize(f(nums.clone()))
            )
        }
    }

    @Test
    fun unsortedInputWithDuplicates() {
        val nums = intArrayOf(2, 1, 2)
        val expected = listOf(
            emptyList(),
            listOf(1),
            listOf(2),
            listOf(1, 2),
            listOf(2, 2),
            listOf(1, 2, 2),
        )

        impls.forEach { f ->
            assertEquals(
                normalize(expected),
                normalize(f(nums.clone()))
            )
        }
    }

    @Test
    fun negativeNumbers() {
        val nums = intArrayOf(-1, -1, 2)
        val expected = listOf(
            emptyList(),
            listOf(-1),
            listOf(2),
            listOf(-1, -1),
            listOf(-1, 2),
            listOf(-1, -1, 2),
        )

        impls.forEach { f ->
            assertEquals(
                normalize(expected),
                normalize(f(nums.clone()))
            )
        }
    }

    private fun normalize(subsets: List<List<Int>>): List<List<Int>> {
        return subsets
            .map { it.toList() }
            .sortedWith(
                compareBy<List<Int>>({ it.size }, { it.joinToString(",") })
            )
    }
}