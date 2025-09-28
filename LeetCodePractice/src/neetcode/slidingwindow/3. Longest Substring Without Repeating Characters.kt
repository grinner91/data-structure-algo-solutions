package neetcode.slidingwindow

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun lengthOfLongestSubstring(s: String): Int {
    var l = 0
    var maxLen = 0
    val set = mutableSetOf<Char>()
    s.forEachIndexed { r, ch ->
        while (ch in set) {
            set.remove(s[l])
            l += 1
        }
        set += ch
        maxLen = maxOf(maxLen, r - l + 1)
    }
    return maxLen
}

class LengthOfLongestSubstringTest {
    @Test
    fun test1() {
        assertEquals(3, lengthOfLongestSubstring("abcabcbb"))
    }

    @Test
    fun test2() {
        assertEquals(1, lengthOfLongestSubstring("bbbbb"))
    }

    @Test
    fun test3() {
        assertEquals(3, lengthOfLongestSubstring("pwwkew"))
    }

}