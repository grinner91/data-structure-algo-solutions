package neetcode.backtracking

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SolutionCombinationSum {
    //backtracking, TC O(2^t/m), SC O(2^t/m)
    fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
        if (candidates.isEmpty() || target <= 0) return emptyList()

        val result = mutableListOf<List<Int>>()
        candidates.sort()

        fun dfs(i: Int, cur: MutableList<Int>, total: Int) {
            if (total == target) {
                result.add(ArrayList(cur))
                return
            }
            for (j in i until candidates.size) {
                val next = candidates[j]
                if (total + next > target) break
                cur.add(next)
                dfs(j, cur, total + next)
                //remove last elem
                cur.removeAt(cur.lastIndex)
            }
        }
        dfs(0, mutableListOf(), 0)
        return result
    }
}

class CombinationSumTest {
    private val sut = SolutionCombinationSum()
    private fun normalize(ans: List<List<Int>>): List<List<Int>> =
        ans.map { it.sorted() }.sortedWith(compareBy({ it.size }, { it.joinToString(",") }))

    @Test
    fun basicExample_1() {
        val candidates = intArrayOf(2, 3, 6, 7)
        val target = 7
        val expected = listOf(
            listOf(7),
            listOf(2, 2, 3)
        )
        val exp = normalize(expected)
        val act = normalize(sut.combinationSum(candidates, target))
        assertEquals(exp, act)
    }

    @Test
    fun basicExample_2() {
        val candidates = intArrayOf(2, 3, 5)
        val target = 8
        val expected = listOf(
            listOf(2, 2, 2, 2),
            listOf(2, 3, 3),
            listOf(3, 5)
        )
        assertEquals(
            normalize(expected),
            normalize(sut.combinationSum(candidates, target))
        )
    }

    @Test
    fun noSolution() {
        val candidates = intArrayOf(5, 10)
        val target = 3
        val expected = emptyList<List<Int>>()
        assertEquals(
            normalize(expected),
            normalize(sut.combinationSum(candidates, target))
        )
    }

    @Test
    fun singleCandidateMultipleUses() {
        val candidates = intArrayOf(3)
        val target = 9
        val expected = listOf(listOf(3, 3, 3))
        assertEquals(
            normalize(expected),
            normalize(sut.combinationSum(candidates, target))
        )
    }

    @Test
    fun emptyCandidates() {
        val candidates = intArrayOf()
        val target = 7
        val expected = emptyList<List<Int>>()
        assertEquals(
            normalize(expected),
            normalize(sut.combinationSum(candidates, target))
        )
    }

    @Test
    fun targetZero() {
        val candidates = intArrayOf(2, 3)
        val target = 0
        val expected = emptyList<List<Int>>() // defined as no combinations for non-positive target here
        assertEquals(
            normalize(expected),
            normalize(sut.combinationSum(candidates, target))
        )
    }
}
