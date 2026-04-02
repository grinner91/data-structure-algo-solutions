package neetcode.backtracking

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CombinationSumBacktrackingRecursion {
    fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
        val res = mutableListOf<List<Int>>()
        val path = mutableListOf<Int>()
        fun dfs(i: Int, total: Int) {
            if (total == target) {
                res.add(path.toList())
                return
            }
            if (i >= candidates.size || total > target) {
                return
            }
            path.add(candidates[i])
            dfs(i, total + candidates[i])
            path.removeLast()
            dfs(i + 1, total)
        }
        dfs(0, 0)
        return res
    }
}

//1st
class CombinationSumBacktrackingLoop {
    fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
        val res = mutableListOf<List<Int>>()
        val path = mutableListOf<Int>()

        candidates.sort()

        fun backtrack(start: Int, remaining: Int) {
            if (remaining == 0) {
                res.add(path.toList())
                return
            }

            for (i in start until candidates.size) {
                val x = candidates[i]
                if (x > remaining) break

                path.add(x)
                backtrack(i, remaining - x)
                path.removeLast() //backtrack to previous state
            }
        }
        backtrack(0, target)
        return res
    }
}


class CombinationSumTest {

    private val impls = listOf(
        CombinationSumBacktrackingLoop()::combinationSum,
//        CombinationSumBacktrackingRecursion()::combinationSum
    )

    @Test
    fun example1() {
        val candidates = intArrayOf(2, 3, 6, 7)
        val target = 7

        val expected = listOf(
            listOf(2, 2, 3),
            listOf(7)
        )

        impls.forEach { f ->
            assertCombinationEquals(expected, f(candidates, target))
        }
    }

    @Test
    fun example2() {
        val candidates = intArrayOf(2, 3, 5)
        val target = 8

        val expected = listOf(
            listOf(2, 2, 2, 2),
            listOf(2, 3, 3),
            listOf(3, 5)
        )

        impls.forEach { f ->
            assertCombinationEquals(expected, f(candidates, target))
        }
    }

    @Test
    fun noSolution() {
        val candidates = intArrayOf(2)
        val target = 1

        val expected = emptyList<List<Int>>()

        impls.forEach { f ->
            assertCombinationEquals(expected, f(candidates, target))
        }
    }

    @Test
    fun singleCandidateRepeatedManyTimes() {
        val candidates = intArrayOf(1)
        val target = 2

        val expected = listOf(
            listOf(1, 1)
        )

        impls.forEach { f ->
            assertCombinationEquals(expected, f(candidates, target))
        }
    }

    @Test
    fun targetEqualsCandidate() {
        val candidates = intArrayOf(4, 5, 8)
        val target = 5

        val expected = listOf(
            listOf(5)
        )

        impls.forEach { f ->
            assertCombinationEquals(expected, f(candidates, target))
        }
    }

    @Test
    fun largerMix() {
        val candidates = intArrayOf(2, 3, 4)
        val target = 7

        val expected = listOf(
            listOf(2, 2, 3),
            listOf(3, 4)
        )

        impls.forEach { f ->
            assertCombinationEquals(expected, f(candidates, target))
        }
    }

    private fun assertCombinationEquals(
        expected: List<List<Int>>,
        actual: List<List<Int>>,
    ) {
        assertEquals(normalize(expected), normalize(actual))
    }

    private fun normalize(combinations: List<List<Int>>): List<List<Int>> {
        return combinations
            .map { it.sorted() }
            .sortedWith(compareBy<List<Int>>({ it.size }, { it.joinToString(",") }))
    }
}