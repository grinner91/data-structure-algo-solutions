package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.max

fun lengthOfLongestSubstring(s: String): Int {
    val subStr = mutableSetOf<Char>()
    var maxSubLen = 0
    var left = 0
    for (right in s.indices) {
        while (s[right] in subStr) {
            subStr.remove(s[left])
            left++
        }
        subStr.add(s[right])
        maxSubLen = max(maxSubLen, subStr.size)
    }
    return maxSubLen
}

fun lengthOfLongestSubstring2(s: String): Int {
    val freqMap = mutableMapOf<Char, Int>()
    var maxSubLen = 0
    var left = 0
    var right = 0
    while (right < s.length) {
        val rightChar = s[right]
        freqMap[rightChar] = freqMap.getOrDefault(rightChar, 0) + 1
        while (freqMap[rightChar]!! > 1) {
            val leftChar = s[left]
            freqMap[leftChar] = freqMap[leftChar]!! - 1
            left++
        }
        maxSubLen = max(maxSubLen, right - left + 1)
        right++
    }
    return maxSubLen
}

class LongestSubstringTests {
    @Test
    fun `should return 3 when given "abcabcbb"`() {
        assertEquals(
            3,
            lengthOfLongestSubstring("abcabcbb")
        )
    }

    @Test
    fun `should return 1 when given "bbbbb"`() {
        assertEquals(
            1,
            lengthOfLongestSubstring("bbbbb")
        )
    }


    @Test
    fun `should return 3 when given "pwwkew"`() {
        assertEquals(
            3,
            lengthOfLongestSubstring("pwwkew")
        )
    }
}