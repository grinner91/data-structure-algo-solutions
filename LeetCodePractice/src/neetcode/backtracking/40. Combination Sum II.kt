package neetcode.backtracking

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CombinationSumIIBacktrackLoop {
    fun combinationSum2(candidates: IntArray, target: Int): List<List<Int>> {
        val res = mutableListOf<List<Int>>()
        val comb = mutableListOf<Int>()

        candidates.sort()

        fun dfs(start: Int, remaining: Int) {
            if (remaining == 0) {
                res.add(comb.toList())
                return
            }
            for (i in start until candidates.size) {
                val x = candidates[i]
                if (i > start && x == candidates[i - 1]) continue //skip duplicates
                if (x > remaining) break

                comb.add(x)
                dfs(i + 1, remaining - x)
                comb.removeLast()
            }
        }

        dfs(0, target)

        return res
    }
}

class CombinationSumIITest {

    private val impls = listOf(
        CombinationSumIIBacktrackLoop()::combinationSum2
    )

    @Test
    fun example1() {
        val candidates = intArrayOf(10, 1, 2, 7, 6, 1, 5)
        val target = 8
        val expected = listOf(
            listOf(1, 1, 6),
            listOf(1, 2, 5),
            listOf(1, 7),
            listOf(2, 6),
        )

        impls.forEach { f ->
            assertEquals(
                normalize(expected),
                normalize(f(candidates.copyOf(), target))
            )
        }
    }

    @Test
    fun example2() {
        val candidates = intArrayOf(2, 5, 2, 1, 2)
        val target = 5
        val expected = listOf(
            listOf(1, 2, 2),
            listOf(5),
        )

        impls.forEach { f ->
            assertEquals(
                normalize(expected),
                normalize(f(candidates.copyOf(), target))
            )
        }
    }

    @Test
    fun noSolution() {
        val candidates = intArrayOf(3, 4, 5)
        val target = 2
        val expected = emptyList<List<Int>>()

        impls.forEach { f ->
            assertEquals(
                normalize(expected),
                normalize(f(candidates.copyOf(), target))
            )
        }
    }

    @Test
    fun singleExactMatch() {
        val candidates = intArrayOf(3)
        val target = 3
        val expected = listOf(listOf(3))

        impls.forEach { f ->
            assertEquals(
                normalize(expected),
                normalize(f(candidates.copyOf(), target))
            )
        }
    }

    @Test
    fun allDuplicatesButSingleValidCombination() {
        val candidates = intArrayOf(1, 1, 1, 1, 1)
        val target = 3
        val expected = listOf(listOf(1, 1, 1))

        impls.forEach { f ->
            assertEquals(
                normalize(expected),
                normalize(f(candidates.copyOf(), target))
            )
        }
    }

    @Test
    fun multipleDuplicatesDifferentValidAnswers() {
        val candidates = intArrayOf(1, 1, 2, 2, 2, 5)
        val target = 5
        val expected = listOf(
            listOf(1, 2, 2),
            listOf(5),
        )

        impls.forEach { f ->
            assertEquals(
                normalize(expected),
                normalize(f(candidates.copyOf(), target))
            )
        }
    }

//    @Test
//    fun targetZeroReturnsEmptyCombination() {
//        val candidates = intArrayOf(1, 2, 3)
//        val target = 0
//        val expected = listOf(emptyList())
//
//        impls.forEach { f ->
//            assertEquals(
//                normalize(expected),
//                normalize(f(candidates.copyOf(), target))
//            )
//        }
//    }

    private fun normalize(combinations: List<List<Int>>): List<List<Int>> {
        return combinations
            .map { it.sorted() }
            .sortedWith(compareBy<List<Int>>({ it.size }, { it.joinToString(",") }))
    }
}