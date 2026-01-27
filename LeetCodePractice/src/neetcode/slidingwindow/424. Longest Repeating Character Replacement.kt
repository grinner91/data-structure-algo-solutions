package neetcode.slidingwindow

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LongestRepeatingCharacterReplacementBrutForce {
    //TC O(n^2)
    //SP O(n)
    fun characterReplacement(s: String, k: Int): Int {
        val n = s.length
        var res = 0
        for (i in 0 until n) {
            val freq = HashMap<Char, Int>()
            var maxFre = 0
            for (j in i until n) {
                val ch = s[j]

                freq[ch] = (freq[ch] ?: 0) + 1
                maxFre = maxOf(maxFre, freq[ch] ?: 0)

                val len = (j - i + 1)
                if (len - maxFre <= k) {
                    res = maxOf(res, len)
                }
            }
        }
        return res
    }
}

class LongestRepeatingCharacterReplacementSlidingWindowOptimal {
    //TC O(n), SP O(n)
    fun characterReplacement(s: String, k: Int): Int {
        val n = s.length
        var res = 0
        val freq = HashMap<Char, Int>()
        var maxFre = 0
        var l = 0

        for (r in 0 until n) {
            freq[s[r]] = (freq[s[r]] ?: 0) + 1
            maxFre = maxOf(maxFre, freq[s[r]] ?: 0)
            while ((r - l + 1 - maxFre) > k) {
                freq[s[l]] = (freq[s[l]] ?: 0) - 1
                l++
            }
            res = maxOf(res, r - l + 1)
        }
        return res
    }
}

class LongestRepeatingCharacterReplacementTest {

    private val impls = listOf(
        LongestRepeatingCharacterReplacementBrutForce()::characterReplacement,
        LongestRepeatingCharacterReplacementSlidingWindowOptimal()::characterReplacement,
//        SolutionSlidingWindow()::characterReplacement,
    )

    data class Case(val s: String, val k: Int, val expected: Int)

    @Test
    fun examples() {
        val cases = listOf(
            Case("ABAB", 2, 4),
            Case("AABABBA", 1, 4),
        )
        cases.forEach { (s, k, expected) ->
            impls.forEach { f ->
                assertEquals(expected, f(s, k), "Failed for s=$s k=$k on $f")
            }
        }
    }

    @Test
    fun singleCharAndZeroK() {
        val cases = listOf(
            Case("A", 0, 1),
            Case("AAAA", 0, 4),
            Case("ABCD", 0, 1),
        )
        cases.forEach { (s, k, expected) ->
            impls.forEach { f ->
                assertEquals(expected, f(s, k), "Failed for s=$s k=$k on $f")
            }
        }
    }

    @Test
    fun largeKCanCoverAll() {
        val cases = listOf(
            Case("ABCDE", 10, 5),
            Case("BAAA", 3, 4),
        )
        cases.forEach { (s, k, expected) ->
            impls.forEach { f ->
                assertEquals(expected, f(s, k), "Failed for s=$s k=$k on $f")
            }
        }
    }

    @Test
    fun trickyWindows() {
        val cases = listOf(
            Case("ABAA", 0, 2),  // "AA"
            Case("ABAA", 1, 4),  // replace 'B' -> "AAAA"
            Case("BAAAB", 2, 5), // replace both 'B' -> "AAAAA"
            Case("AABBBCC", 1, 4), // "ABBB" -> 4
        )
        cases.forEach { (s, k, expected) ->
            impls.forEach { f ->
                assertEquals(expected, f(s, k), "Failed for s=$s k=$k on $f")
            }
        }
    }
}
