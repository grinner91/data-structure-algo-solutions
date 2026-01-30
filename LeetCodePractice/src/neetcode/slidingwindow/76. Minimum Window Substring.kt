package neetcode.slidingwindow

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//
////hash map
//fun minWindow1(s: String, t: String): String {
//    if (s.isEmpty() || t.isEmpty()) return ""
//    val need = t.groupingBy { it }.eachCount()
//    var resStart = -1
//    var resLen = Int.MAX_VALUE
//    for (l in s.indices) {
//        val have = mutableMapOf<Char, Int>()
//        for (r in l until s.length) {
//            val ch = s[r]
//            have[ch] = (have[ch] ?: 0) + 1
//            val containsAll = need.all { (k, v) -> (have[k] ?: 0) >= v }
//            if (containsAll && (r - l + 1) < resLen) {
//                resStart = l
//                resLen = (r - l + 1)
//            }
//        }
//    }
//    return if (resStart == -1) "" else s.substring(resStart, resStart + resLen)
//}
//
////sliding window
//fun minWindow(s: String, t: String): String {
//    if (t.isEmpty() || s.isEmpty()) return ""
//    val tarFreq = t.groupingBy { it }.eachCount()
//    val winFreq = mutableMapOf<Char, Int>()
//    val req = tarFreq.size
//    var have = 0
//    var l = 0
//    var start = -1
//    var minWinLen = Int.MAX_VALUE
//    s.forEachIndexed { r, c ->
//        winFreq[c] = (winFreq[c] ?: 0) + 1
//        if (c in tarFreq && winFreq[c] == tarFreq[c]) {
//            have++
//        }
//        while (have == req) {
//            if ((r - l + 1) < minWinLen) {
//                minWinLen = r - l + 1
//                start = l
//            }
//            val chLeft = s[l]
//            l += 1
//            winFreq[chLeft] = (winFreq[chLeft] ?: 0) - 1
//            if (chLeft in tarFreq && (winFreq[chLeft] ?: 0) < (tarFreq[chLeft] ?: 0)) {
//                have -= 1
//            }
//        }
//    }
//    return if (start == -1) "" else s.substring(start, start + minWinLen)
//}

class MinimumWindowSubstringBruteForce {
    fun minWindow(s: String, t: String): String {
        if (t.isEmpty() || t.length > s.length) return ""
        val countT = t.asIterable().groupingBy { it }.eachCount()
        var start = 0
        var minLen = Int.MAX_VALUE
        for (l in s.indices) {
            val countWin = HashMap<Char, Int>()
            for (r in l until s.length) {
                val ch = s[r]
                countWin[ch] = (countWin[ch] ?: 0) + 1
                val allMatch = countT.all { (k, v) -> (countWin[k] ?: 0) >= v }
                if (allMatch) {
                    val len = r - l + 1
                    if (minLen > len) {
                        start = l
                        minLen = len
                    }
                }
            }
        }
        return if (minLen < Int.MAX_VALUE)
            s.substring(start, start + minLen)
        else ""
    }
}

class MinimumWindowSubstringSlidingWindow {
    fun minWindow(s: String, t: String): String {
        if (t.isEmpty() || t.length > s.length) return ""
        val countT = t.groupingBy { it }.eachCount()
        val need = countT.size
        val countWin = HashMap<Char, Int>()
        var have = 0
        var start = 0
        var minLen = Int.MAX_VALUE
        var l = 0
        for (r in s.indices) {
            val rch = s[r] //expand window to right
            countWin[rch] = (countWin[rch] ?: 0) + 1
            if (rch in countT && countT[rch] == countWin[rch]) {
                have++
            }
            while (have == need) {
                val len = r - l + 1
                if (minLen > len) {
                    start = l
                    minLen = len
                }
                val lch = s[l]
                countWin[lch] = (countWin[lch] ?: 0) - 1
                if (lch in countT && (countWin[lch] ?: 0) < (countT[lch] ?: 0)) {
                    have--
                }
                l++ //shrink window from left
            }
        }
        return if (minLen == Int.MAX_VALUE) ""
        else s.substring(start, start + minLen)
    }
}

class MinimumWindowSubstringTest {

    private val impls = listOf(
        MinimumWindowSubstringSlidingWindow()::minWindow,
        //  MinimumWindowSubstringBruteForce()::minWindow,
    )

    @Test
    fun example1() {
        impls.forEach { f ->
            assertEquals("BANC", f("ADOBECODEBANC", "ABC"))
        }
    }

    @Test
    fun example2_singleChar() {
        impls.forEach { f ->
            assertEquals("a", f("a", "a"))
        }
    }

    @Test
    fun example3_impossible() {
        impls.forEach { f ->
            assertEquals("", f("a", "aa"))
        }
    }

    @Test
    fun duplicatesInT() {
        impls.forEach { f ->
            assertEquals("AAB", f("AAAB", "AAB"))
        }
    }

    @Test
    fun multiplePossibleChooseMin() {
        impls.forEach { f ->
            assertEquals(
                "ab",
                f("bba", "ab").let { it }) // expected "ba"? let's validate actual minimal: s="bba", t="ab" => "ba"
        }
    }

    @Test
    fun unicodeOrNonAsciiNote() {
        // These implementations use ASCII array (128).
        // If your inputs can be full Unicode, use HashMap-based counts instead.
        impls.forEach { f ->
            assertEquals("ba", f("bba", "ab"))
        }
    }
}
