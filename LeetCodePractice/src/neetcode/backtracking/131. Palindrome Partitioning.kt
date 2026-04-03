package neetcode.backtracking

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*

Time
Worst case: O(n * 2^n) to generate partitions, plus palindrome checking
More precisely often written as O(n^2 * 2^n) in worst case

* */
class PalindromePartitioningBacktracking {
    fun partition(s: String): List<List<String>> {
        val result = mutableListOf<List<String>>()
        val parts = mutableListOf<String>()

        fun isPalindrome(left: Int, right: Int): Boolean {
            var l = left
            var r = right
            while (l < r) {
                if (s[l] != s[r]) return false
                l++
                r--
            }
            return true
        }

        fun backtrack(start: Int) {
            if (start == s.length) {
                result.add(parts.toList())
                return
            }

            for (end in start until s.length) {
                if (isPalindrome(start, end).not()) continue

                parts.add(s.substring(start, end + 1))
                backtrack(end + 1)
                parts.removeLast()
            }
        }

        backtrack(0)

        return result
    }
}

class PalindromePartitioningTest {

    private val impls = listOf(
        PalindromePartitioningBacktracking()::partition,
    )

    @Test
    fun example1() {
        val expected = normalized(
            listOf("a", "a", "b"),
            listOf("aa", "b"),
        )

        impls.forEach { partition ->
            val actual = normalized(partition("aab"))
            assertEquals(expected, actual)
        }
    }

    @Test
    fun singleCharacter() {
        val expected = normalized(
            listOf("a"),
        )

        impls.forEach { partition ->
            val actual = normalized(partition("a"))
            assertEquals(expected, actual)
        }
    }

    @Test
    fun allSameCharacters() {
        val expected = normalized(
            listOf("a", "a", "a"),
            listOf("a", "aa"),
            listOf("aa", "a"),
            listOf("aaa"),
        )

        impls.forEach { partition ->
            val actual = normalized(partition("aaa"))
            assertEquals(expected, actual)
        }
    }

    @Test
    fun noLongerPalindromeChoices() {
        val expected = normalized(
            listOf("a", "b", "c"),
        )

        impls.forEach { partition ->
            val actual = normalized(partition("abc"))
            assertEquals(expected, actual)
        }
    }

    @Test
    fun multiplePartitionChoices() {
        val expected = normalized(
            listOf("e", "f", "e"),
            listOf("efe"),
        )

        impls.forEach { partition ->
            val actual = normalized(partition("efe"))
            assertEquals(expected, actual)
        }
    }

    @Test
    fun largerExample() {
        val expected = normalized(
            listOf("a", "b", "a", "c"),
            listOf("aba", "c"),
        )

        impls.forEach { partition ->
            val actual = normalized(partition("abac"))
            assertEquals(expected, actual)
        }
    }

    private fun normalized(vararg partitions: List<String>): List<List<String>> {
        return normalized(partitions.toList())
    }

    private fun normalized(partitions: List<List<String>>): List<List<String>> {
        return partitions
            .map { it.toList() }
            .sortedWith(compareBy({ it.size }, { it.joinToString("|") }))
    }
}