package neetcode.slidingwindow

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PermutationInStringBrutForce {
    //TC O(m*nlogn) , SC O(n)
    //n - s1.length
    //m - s2.length
    fun checkInclusion(s1: String, s2: String): Boolean {
        val n = s1.length
        val m = s2.length
        if (n > m) return false
        val p1 = s1.toCharArray().sorted()
//            .apply { sort() }
//            .concatToString()
        for (i in 0..(m - n)) {
            val p2 = s2.substring(i, i + n)
                .toCharArray().sorted()
//                .apply { sort() }
//                .concatToString()

            if (p1 == p2) return true
        }
        return false
    }
}

class PermutationInStringFreqHashTable {
    //TC O(n * m) , SC O(n)
    fun checkInclusion(s1: String, s2: String): Boolean {
        if (s1.length > s2.length) return false
        val freq1 = HashMap<Char, Int>()
        for (ch in s1) {
            freq1[ch] = (freq1[ch] ?: 0) + 1
        }

        for (i in s2.indices) {
            val freq2 = HashMap<Char, Int>()
            var match = 0
            for (j in i until s2.length) {
                val ch = s2[j]
                freq2[ch] = (freq2[ch] ?: 0) + 1
                if ((freq2[ch] ?: 0) > (freq1[ch] ?: 0)) break
                if ((freq2[ch] ?: 0) == (freq1[ch] ?: 0)) match++
                if (match == freq1.size) return true
            }
        }
        return false
    }
}

class PermutationInStringSlidingWindowMatches {
    fun checkInclusion(s1: String, s2: String): Boolean {
        if(s1.length > s2.length) return false
        val n = 26 //ALPHABET_SIZE
        val a = 'a' //Base
        val fre1 = IntArray(n)
        val fre2 = IntArray(n)
        for (i  in s1.indices) {
            fre1[s1[i] - a]++
            fre2[s2[i] - a]++
        }
        var match = 0
        for (i in 0 until n){
            if(fre1[i] == fre2[i]) match++
        }
        var l = 0
        for (r in s1.length until  s2.length) {
            if(match == n) return true

            val right = s2[r] - a
            fre2[right]++
            if(fre1[right] == fre2[right]) match++
            else if(fre1[right] + 1 == fre2[right]) match--

            val left = s2[l] - a
            fre2[left]--
            if(fre1[left] == fre2[left]) match++
            else if(fre1[left] - 1 == fre2[left]) match--

            l++
        }
        return match == n
    }
}

class PermutationInStringTest {

    private val impls = listOf(
//        PermutationInStringBrutForce()::checkInclusion,
//        PermutationInStringFreqHashTable()::checkInclusion,
        PermutationInStringSlidingWindowMatches()::checkInclusion
    )

    @Test
    fun example_true() {
        impls.forEach { f ->
            assertTrue(f("ab", "eidbaooo"))
        }
    }

    @Test
    fun example_false() {
        impls.forEach { f ->
            assertFalse(f("ab", "eidboaoo"))
        }
    }

    @Test
    fun s1LongerThanS2_false() {
        impls.forEach { f ->
            assertFalse(f("abcd", "abc"))
        }
    }

    @Test
    fun exactMatch_true() {
        impls.forEach { f ->
            assertTrue(f("abc", "abc"))
        }
    }

    @Test
    fun permutationAtStart_true() {
        impls.forEach { f ->
            assertTrue(f("abc", "cbaXXXX".lowercase()))
        }
    }

    @Test
    fun permutationAtEnd_true() {
        impls.forEach { f ->
            assertTrue(f("adc", "dcda")) // "cda" is a permutation of "adc"
        }
    }

    @Test
    fun repeatedChars_true() {
        impls.forEach { f ->
            assertTrue(f("aab", "baa"))
            assertTrue(f("aab", "aaab")) // window "aab"
        }
    }

    @Test
    fun repeatedChars_false() {
        impls.forEach { f ->
            assertFalse(f("aab", "ab")) // too short
            assertFalse(f("aab", "abb")) // no window with two a's
        }
    }

    @Test
    fun singleCharCases() {
        impls.forEach { f ->
            assertTrue(f("a", "a"))
            assertTrue(f("a", "ba"))
            assertFalse(f("a", "bbb"))
        }
    }
}
