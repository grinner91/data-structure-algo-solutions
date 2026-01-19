package neetcode.dynamic1d

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class SolutionNumDecodingsRecursion {
    fun numDecodings(s: String): Int {
        if (s.isEmpty()) return 0

        return dfs(s, 0)
    }

    fun dfs(s: String, i: Int): Int {
        if (i == s.length) return 1
        if (s[i] == '0') return 0

        var res = dfs(s, i + 1)

        if (i < s.length - 1) {
            if (s[i] == '1' || (s[i] == '2' && s[i + 1] in "0123456")) {
                res += dfs(s, i + 2)
            }
        }
        return res
    }
}

class SolutionNumDecodingsDPTopDownMemo {
    //recursion with memoization
    fun numDecodings(s: String): Int {
        if (s.isEmpty() || s[0] == '0') return 0

        val memo = mutableMapOf(s.length to 1)

        fun dfs(i: Int): Int {
            if (i == s.length) return 1
            if (s[i] == '0') return 0

            if (i in memo) return memo[i]!!

            var ways = dfs(i + 1)
            if (i + 1 < s.length) {
                val twoDigits = (s[i] - '0') * 10 + (s[i + 1] - '0')
                if (twoDigits in 10..26) {
                    ways += dfs(i + 2)
                }
            }
            memo[i] = ways
            return ways
        }
        return dfs(0)
    }
}

//chatgpt unit tests
class DecodeWaysTest {

    // Single instance used in all tests
    //private val sut = SolutionNumDecodingsRecursion()
    private val sut = SolutionNumDecodingsDPTopDownMemo()

    @Test
    fun `empty string returns 0`() {
        assertEquals(0, sut.numDecodings(""))
    }

    @Test
    fun `single zero returns 0`() {
        assertEquals(0, sut.numDecodings("0"))
    }

    @Test
    fun `leading zero string returns 0`() {
        assertEquals(0, sut.numDecodings("06"))
    }

    @Test
    fun `single valid digit returns 1`() {
        assertEquals(1, sut.numDecodings("7"))
    }

    @Test
    fun `simple two digits with two ways`() {
        // "12" -> "AB", "L"
        assertEquals(2, sut.numDecodings("12"))
    }

    @Test
    fun `two digits but only one valid way`() {
        // "27" -> only "BG" (27 is not valid as a letter)
        assertEquals(1, sut.numDecodings("27"))
    }

    @Test
    fun `example 226 has three ways`() {
        // "226" -> "BZ", "VF", "BBF"
        assertEquals(3, sut.numDecodings("226"))
    }

    @Test
    fun `string with internal zero valid`() {
        // "101" -> "JA"
        assertEquals(1, sut.numDecodings("101"))
    }

    @Test
    fun `string with invalid double zero`() {
        // "100" -> "10" + "0" (invalid), so 0
        assertEquals(0, sut.numDecodings("100"))
    }

    @Test
    fun `complex example with zeros`() {
        // Known LeetCode example: "11106" -> 2
        assertEquals(2, sut.numDecodings("11106"))
    }

    @Test
    fun `long string for performance`() {
        // "1111111111" (10 times '1') -> Fibonacci-like count
        // For length 10, answer is F(11) = 89
        assertEquals(89, sut.numDecodings("1111111111"))
    }
}
