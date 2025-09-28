package neetcode.slidingwindow

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


//hash map
fun minWindow1(s: String, t: String): String {
    if (s.isEmpty() || t.isEmpty()) return ""
    val need = t.groupingBy { it }.eachCount()
    var resStart = -1
    var resLen = Int.MAX_VALUE
    for (l in s.indices) {
        val have = mutableMapOf<Char, Int>()
        for (r in l until s.length) {
            val ch = s[r]
            have[ch] = (have[ch] ?: 0) + 1
            val containsAll = need.all { (k, v) -> (have[k] ?: 0) >= v }
            if (containsAll && (r - l + 1) < resLen) {
                resStart = l
                resLen = (r - l + 1)
            }
        }
    }
    return if (resStart == -1) "" else s.substring(resStart, resStart + resLen)
}

//sliding window
fun minWindow(s: String, t: String): String {
    if (t.isEmpty() || s.isEmpty()) return ""
    val tarFreq = t.groupingBy { it }.eachCount()
    val winFreq = mutableMapOf<Char, Int>()
    val req = tarFreq.size
    var have = 0
    var l = 0
    var start = -1
    var minWinLen = Int.MAX_VALUE
    s.forEachIndexed { r, c ->
        winFreq[c] = (winFreq[c] ?: 0) + 1
        if (c in tarFreq && winFreq[c] == tarFreq[c]) {
            have++
        }
        while (have == req) {
            if ((r - l + 1) < minWinLen) {
                minWinLen = r - l + 1
                start = l
            }
            val chLeft = s[l]
            l += 1
            winFreq[chLeft] = (winFreq[chLeft] ?: 0) - 1
            if (chLeft in tarFreq && (winFreq[chLeft] ?: 0) < (tarFreq[chLeft] ?: 0)) {
                have -= 1
            }
        }
    }
    return if (start == -1) "" else s.substring(start, start + minWinLen)
}

class MinWindowTest {
    @Test
    fun test1() {
        assertEquals(minWindow("ADOBECODEBANC", "ABC"), "BANC")
    }

    @Test
    fun test2() {
        assertEquals(minWindow("a", "a"), "a")
    }

    @Test
    fun test3() {
        assertEquals(minWindow("OUZODYXAZV", "XYZ"), "YXAZ")
    }

    @Test
    fun test4() {
        assertEquals(minWindow("x", "xy"), "")
    }
}