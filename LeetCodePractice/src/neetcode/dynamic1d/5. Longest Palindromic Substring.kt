package neetcode.dynamic1d

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SolutionLongestPalindromeBrutForce {
    fun longestPalindrome(str: String): String {
        if (str.isEmpty()) return ""
        if (str.length < 2) return str

        var maxLen = Int.MIN_VALUE
        var pal = ""
        for (i in str.indices) {
            for (j in i until str.length) {
                var left = i
                var right = j
                while (left < right && str[left] == str[right]) {
                    left++
                    right--
                }
                if (left >= right && (j - i + 1) > maxLen) {
                    maxLen = (j - i + 1)
                    pal = str.substring(i, j + 1)
                }
            }
        }
        return pal
    }
}

class SolutionLongestPalindromeDP {
    fun longestPalindrome(s: String): String {
        val n = s.length
        if (n < 2) return s

        val dp = Array(n) { BooleanArray(n) }

        var subStart = 0
        var subLen = 1

        for (i in n - 1 downTo 0) {
            for (j in i until n) {
                if (s[i] == s[j] &&
                    (j - i <= 2 || dp[i + 1][j - 1])
                ) {
                    dp[i][j] = true
                    val curLen = j - i + 1
                    if (curLen > subLen) {
                        subLen = curLen
                        subStart = i
                    }
                }
            }
        }
        return s.substring(subStart, subStart + subLen)
    }
}


class SolutionTest {
    //val sut = SolutionLongestPalindromeBrutForce()
    val sut = SolutionLongestPalindromeDP()


    @Test
    fun example1() {
        val result = sut.longestPalindrome("babad")
        // "bab" or "aba" are both correct
        assertTrue(result == "bab" || result == "aba")
    }

    @Test
    fun example2() {
        val result = sut.longestPalindrome("cbbd")
        assertEquals("bb", result)
    }

    @Test
    fun singleCharacter() {
        val result = sut.longestPalindrome("a")
        assertEquals("a", result)
    }

    @Test
    fun twoSameCharacters() {
        val result = sut.longestPalindrome("aa")
        assertEquals("aa", result)
    }

    @Test
    fun twoDifferentCharacters() {
        val result = sut.longestPalindrome("ab")
        assertTrue(result == "a" || result == "b")
    }

    @Test
    fun allSameCharacters() {
        val result = sut.longestPalindrome("aaaaaa")
        assertEquals("aaaaaa", result)
    }

    @Test
    fun palindromeInMiddle() {
        val result = sut.longestPalindrome("xxracecarxx")
        assertEquals("xxracecarxx", result)
    }

    @Test
    fun evenLengthPalindromeInMiddle() {
        val result = sut.longestPalindrome("zabbar")
        assertEquals("abba", result)
    }

    @Test
    fun noPalindromeLongerThanOne() {
        val s = "abcde"
        val result = sut.longestPalindrome(s)
        assertEquals(1, result.length)
        assertTrue(result in s)
    }

    @Test
    fun emptyString() {
        val result = sut.longestPalindrome("")
        assertEquals("", result)
    }
}
