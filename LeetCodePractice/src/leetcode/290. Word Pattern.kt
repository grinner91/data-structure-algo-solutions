package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun wordPattern(pattern: String, s: String): Boolean {
    val words = s.split(' ')
    val chars = pattern.toCharArray()
    if (chars.size != words.size) return false
    val charToWordMap = hashMapOf<Char, String>()
    chars.forEachIndexed { i, ch ->
        if (!charToWordMap.containsKey(ch) && !charToWordMap.containsValue(words[i])) {
            charToWordMap[ch] = words[i]
        } else if (charToWordMap[ch] != words[i]) {
            return false
        }
    }
    return true
}

class WordPatternTests {

    @Test
    fun `should return false when given "aba" and "cat cat cat dog"`() {
        assertEquals(
            false,
            wordPattern("aba", "cat cat cat dog")
        )
    }

    @Test
    fun `should return true when given "abba" and "dog cat cat dog"`() {
        assertEquals(
            true,
            wordPattern("abba", "dog cat cat dog")
        )
    }

    @Test
    fun `should return false when given "abba" and "dog cat cat fish"`() {
        assertEquals(
            false,
            wordPattern("abba", "dog cat cat fish")
        )
    }

    @Test
    fun `should return false when given "aaaa" and "dog cat cat dog"`() {
        assertEquals(
            false,
            wordPattern("aaaa", "dog cat cat fish")
        )
    }


}