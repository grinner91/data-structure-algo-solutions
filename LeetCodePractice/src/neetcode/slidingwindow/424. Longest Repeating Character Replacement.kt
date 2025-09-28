package neetcode.slidingwindow

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//BF
fun characterReplacement(s: String, k: Int): Int {
    var maxLen = 0
    for (l in s.indices) {
        val freq = mutableMapOf<Char, Int>()
        var maxFreqInWin = 0
        for (r in l until s.length) {
            freq[s[r]] = (freq[s[r]] ?: 0) + 1
            maxFreqInWin = maxOf(maxFreqInWin, freq[s[r]]!!)
            val window = r - l + 1
            if (window - maxFreqInWin <= k) {
                maxLen = maxOf(maxLen, window)
            }
        }
    }
    return maxLen
}

//sliding window optimal
fun characterReplacement1(s: String, k: Int): Int {
    var l = 0
    var maxLen = 0
    var maxFreqInWin = 0
    val freq = mutableMapOf<Char, Int>()
    s.forEachIndexed { r, c ->
        freq[c] = (freq[c] ?: 0) + 1
        maxFreqInWin = maxOf(maxFreqInWin, freq[c]!!)
        while (r - l + 1 - maxFreqInWin > k) {
            freq[s[l]] = freq[s[l]]!! - 1
            l += 1
        }
        maxLen = maxOf(maxLen, r - l + 1)
    }
    return maxLen
}

class CharacterReplacementTest {
    @Test
    fun test1() {
        assertEquals(4, characterReplacement("AABABBA", 1))
    }

    @Test
    fun test2() {
        assertEquals(4, characterReplacement("ABAB", 2))
    }
}