package neetcode.backtracking

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//Backtracking (choice)
//O(n * 2^n)
//O(n) recursion + output

class SubsetsBacktrackingChoiceDfs {
    fun subsets(nums: IntArray): List<List<Int>> {
        val res = mutableListOf<List<Int>>()
        val sub = mutableListOf<Int>()

        fun dfs(i: Int) {
            if (i == nums.size) {
                res.add(sub.toList())
                return
            }

            //not included - nums[i]
            dfs(i + 1)

            //included - nums[i]
            sub.add(nums[i])
            dfs(i + 1)
            //backtracking to previous state
            sub.removeAt(sub.lastIndex)
        }
        dfs(0)
        return res
    }
}
/*
LOOP Tree Visualization [5,6,7]
[]
├── [5]
│   ├── [5,6]
│   │   └── [5,6,7]
│   └── [5,7]
├── [6]
│   └── [6,7]
└── [7]
* */
//1st choice
class SubsetsBacktrackingLoop {
    fun subsets(nums: IntArray): List<List<Int>> {
        val res = mutableListOf<List<Int>>()
        val subset = mutableListOf<Int>()
        fun backtrack(start: Int) {
           res.add(subset.toList())
            //N-ary tree - From remaining elements → pick one and continue
            for (i in start until nums.size) {
                subset.add(nums[i])
                backtrack(i + 1)
                subset.removeAt(subset.lastIndex)
            }
        }
        backtrack(0)
        return res
    }
}

class SubsetsTest {

    private val impls = listOf(
//        SubsetsBacktrackingChoiceDfs()::subsets,
        SubsetsBacktrackingLoop()::subsets
    )

    @Test
    fun example1() {
        val nums = intArrayOf(5, 6, 7)
        val expected = listOf(
            listOf(),
            listOf(5),
            listOf(6),
            listOf(7),
            listOf(5, 6),
            listOf(5, 7),
            listOf(6, 7),
            listOf(5, 6, 7),
        )

        impls.forEach { subsets ->
            assertSubsetsEqual(expected, subsets(nums))
        }
    }

    @Test
    fun example2() {
        val nums = intArrayOf(0)
        val expected = listOf(
            listOf(),
            listOf(0),
        )

        impls.forEach { subsets ->
            assertSubsetsEqual(expected, subsets(nums))
        }
    }

    @Test
    fun emptyInput() {
        val nums = intArrayOf()
        val expected = listOf(emptyList<Int>())

        impls.forEach { subsets ->
            assertSubsetsEqual(expected, subsets(nums))
        }
    }

    @Test
    fun twoElements() {
        val nums = intArrayOf(4, 5)
        val expected = listOf(
            listOf(),
            listOf(4),
            listOf(5),
            listOf(4, 5),
        )

        impls.forEach { subsets ->
            assertSubsetsEqual(expected, subsets(nums))
        }
    }

    @Test
    fun negativeNumbers() {
        val nums = intArrayOf(-5, 6)
        val expected = listOf(
            listOf(),
            listOf(-5),
            listOf(6),
            listOf(-5, 6),
        )

        impls.forEach { subsets ->
            assertSubsetsEqual(expected, subsets(nums))
        }
    }

    private fun assertSubsetsEqual(
        expected: List<List<Int>>,
        actual: List<List<Int>>,
    ) {
        assertEquals(normalize(expected), normalize(actual))
    }

    private fun normalize(subsets: List<List<Int>>): List<List<Int>> {
        return subsets
            .map { it.toList() }
            .sortedWith(
                compareBy<List<Int>> { it.size }
                    .thenBy { it.joinToString(",") }
            )
    }
}