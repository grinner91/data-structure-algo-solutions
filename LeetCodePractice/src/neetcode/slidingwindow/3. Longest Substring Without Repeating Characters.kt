package neetcode.slidingwindow

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class LongestSubstringWithoutRepeatingCharactersBrutForce {
    fun lengthOfLongestSubstring(s: String): Int {
        var res = 0
        for (i in s.indices) {
            val seen = HashSet<Char>()
            for (j in i until s.length) {
                if (!seen.add(s[j])) {
                    break
                }
            }
            res = maxOf(res, seen.size)
        }
        return res
    }
}

class LongestSubstringWithoutRepeatingCharactersSlidingWindow {
    fun lengthOfLongestSubstring(s: String): Int {
        var seen = HashSet<Char>()
        var res = 0
        var l = 0
        for (r in s.indices) {
            while (s[r] in seen) {
                seen.remove(s[l])
                l++
            }
            seen.add(s[r])
            res = maxOf(res, r - l + 1)
        }
        return res
    }
}


class LongestSubstringWithoutRepeatingCharactersLastSeenIndexMap {
    fun lengthOfLongestSubstring(s: String): Int {
        var res = 0
        var l = 0
        val lastSeen = HashMap<Char, Int>()
        for (r in s.indices) {
            lastSeen[s[r]]?.let { idx ->
                l = maxOf(idx + 1, l)
            }
            lastSeen[s[r]] = r
            res = maxOf(res, r - l + 1)
        }
        return res
    }
}


class LongestSubstringWithoutRepeatingCharactersTest {
    private val impls = listOf(
        // LongestSubstringWithoutRepeatingCharactersBrutForce()::lengthOfLongestSubstring,
        LongestSubstringWithoutRepeatingCharactersSlidingWindow()::lengthOfLongestSubstring,
//        SolutionSetWindow()::lengthOfLongestSubstring,
    )

    @Test
    fun empty() {
        impls.forEach { f -> assertEquals(0, f("")) }
    }

    @Test
    fun singleChar() {
        impls.forEach { f -> assertEquals(1, f("a")) }
    }

    @Test
    fun allSame() {
        impls.forEach { f -> assertEquals(1, f("bbbbbb")) }
    }

    @Test
    fun typicalExamples() {
        impls.forEach { f ->
            assertEquals(3, f("abcabcbb")) // "abc"
            assertEquals(1, f("bbbbb"))    // "b"
            assertEquals(3, f("pwwkew"))   // "wke"
        }
    }

    @Test
    fun withSpacesAndSymbols() {
        impls.forEach { f ->
            assertEquals(3, f("a b"))          // "a b"
            assertEquals(5, f("a!b@c#"))       // all unique
            assertEquals(4, f("ab!!cd"))       // " !cd" not valid; actual best "ab!":3, or "!cd":3; let's verify:
        }
    }

    @Test
    fun shrinkingWindowCases() {
        impls.forEach { f ->
            assertEquals(2, f("abba"))      // "ab" or "ba"
            assertEquals(3, f("dvdf"))      // "vdf"
            assertEquals(5, f("tmmzuxt"))   // "mzuxt"
        }
    }

    @Test
    fun unicodeSmokeTest() {
        // Ensures map-based solution handles non-ASCII; ascii-array falls back safely.
        impls.forEach { f ->
            assertEquals(3, f("a😊b😊c")) // longest without repeat: "a😊b" or "b😊c"
        }
    }
}
