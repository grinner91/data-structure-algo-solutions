package neetcode.backtracking

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
/*
Time: O(Cn × n) where Cn is the nth Catalan number
Approx: O(4^n / sqrt(n)) valid results, and each string costs up to O(n) to build/copy
Space: O(n) recursion stack, excluding output

If each position has k choices and there are m positions → total = k^m
k = 2 (two symbols)
m = 2n (length)
2^(2n) = ((2^2)^n) = 4^n
* */

class GenerateParenthesesBacktrackingOptimal {
    fun generateParenthesis(n: Int): List<String> {
        val result = mutableListOf<String>()
        val subsets = StringBuilder()

        fun backtrack(open: Int, close: Int) {
            if (open == n && close == n) {
                result.add(subsets.toString())
                return
            }
            if (open < n) {
                subsets.append("(")
                backtrack(open + 1, close)
                subsets.deleteCharAt(subsets.lastIndex)
            }
            if (close < open) {
                subsets.append(")")
                backtrack(open, close + 1)
                subsets.deleteCharAt(subsets.lastIndex)
            }
        }

        backtrack(0, 0)

        return result
    }
}

class GenerateParenthesesTest {
    private val impls = listOf(
        GenerateParenthesesBacktrackingOptimal()::generateParenthesis
    )

    @Test
    fun n1() {
        val expected = setOf("()")

        impls.forEach { f ->
            assertEquals(expected, f(1).toSet())
        }
    }

    @Test
    fun n2() {
        val expected = setOf("(())", "()()")

        impls.forEach { f ->
            assertEquals(expected, f(2).toSet())
        }
    }

    @Test
    fun n3() {
        val expected = setOf(
            "((()))",
            "(()())",
            "(())()",
            "()(())",
            "()()()"
        )

        impls.forEach { f ->
            assertEquals(expected, f(3).toSet())
        }
    }

    @Test
    fun n0() {
        val expected = setOf("")

        impls.forEach { f ->
            assertEquals(expected, f(0).toSet())
        }
    }

    @Test
    fun sizeMatchesCatalanNumbers() {
        val expectedSizes = mapOf(
            0 to 1,
            1 to 1,
            2 to 2,
            3 to 5,
            4 to 14
        )

        impls.forEach { f ->
            expectedSizes.forEach { (n, size) ->
                assertEquals(size, f(n).size)
            }
        }
    }

    @Test
    fun allOutputsAreValidAndUnique() {
        impls.forEach { f ->
            for (n in 0..4) {
                val result = f(n)
                assertEquals(result.size, result.toSet().size)

                result.forEach { s ->
                    assertEquals(2 * n, s.length)
                    assertEquals(true, isValidParentheses(s))
                }
            }
        }
    }

    private fun isValidParentheses(s: String): Boolean {
        var balance = 0
        for (ch in s) {
            if (ch == '(') {
                balance++
            } else {
                balance--
                if (balance < 0) return false
            }
        }
        return balance == 0
    }
}