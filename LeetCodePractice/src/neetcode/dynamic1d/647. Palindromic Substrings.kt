package neetcode.dynamic1d

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SolutionPalindromicSubsBruteForce {
    fun countSubstrings(s: String): Int {
        var res = 0
        for (i in s.indices) {
            for (j in i until s.length) {
                var l = i
                var r = j
                while (l < r && s[l] == s[r]) {
                    l++
                    r--
                }
                if (l >= r) {
                    res++
                }
            }
        }
        return res
    }
}


class SolutionPalindromicSubsTwoPointer {
    fun countSubstrings(s: String): Int {
        val n = s.length
        var res = 0
        fun expandFromCenter(i: Int, j: Int) {
            var l = i
            var r = j
            while (l >= 0 && r < n && s[l] == s[r]) {
                res++
                l--
                r++
            }
        }
        for (c in s.indices) {
            //odd centers
            expandFromCenter(c, c)
            //even centers
            expandFromCenter(c, c + 1)
        }
        return res
    }
}

class PalindromicSubstringsTest {

    //private val solution = SolutionPalindromicSubsBruteForce()
    private val solution = SolutionPalindromicSubsTwoPointer()

    @Test
    fun `example 1 - abc`() {
        val s = "abc"
        // palindromes: "a", "b", "c"
        assertEquals(3, solution.countSubstrings(s))
    }

    @Test
    fun `example 2 - aaa`() {
        val s = "aaa"
        // palindromes: "a","a","a","aa","aa","aaa" → 6
        assertEquals(6, solution.countSubstrings(s))
    }

    @Test
    fun `single character`() {
        val s = "z"
        assertEquals(1, solution.countSubstrings(s))
    }

    @Test
    fun `all same characters`() {
        val s = "aaaa"
        // "a","a","a","a" (4)
        // "aa","aa","aa" (3)
        // "aaa","aaa" (2)
        // "aaaa" (1)
        // total = 10
        assertEquals(10, solution.countSubstrings(s))
    }

    @Test
    fun `mixed characters`() {
        val s = "ababa"
        // palindromes:
        // length 1: a, b, a, b, a  → 5
        // length 2: (none)
        // length 3: aba (0..2), bab (1..3), aba (2..4) → 3
        // length 5: ababa (0..4) → 1
        // total = 9
        assertEquals(9, solution.countSubstrings(s))
    }

    @Test
    fun `no repeated characters`() {
        val s = "abcd"
        // only single letters
        assertEquals(4, solution.countSubstrings(s))
    }

    @Test
    fun `palindromes with even length`() {
        val s = "abba"
        // "a","b","b","a" (4)
        // "bb" (1)
        // "abba" (1)
        // total = 6
        assertEquals(6, solution.countSubstrings(s))
    }

    @Test
    fun `longer string sanity test`() {
        val s = "racecar"
        // Just sanity check against known answer:
        // "r","a","c","e","c","a","r" (7)
        // "cec","aceca","racecar","eca","cec","ara"... etc → total 10
        // Quick manual check or precomputed: 10
        assertEquals(10, solution.countSubstrings(s))
    }
}
