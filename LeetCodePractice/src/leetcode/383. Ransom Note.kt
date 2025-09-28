package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun canConstruct(ransomNote: String, magazine: String): Boolean {
    val magazineFreq = magazine
        .groupingBy { it }
        .eachCount()

    ransomNote.groupingBy { it }
        .eachCount()
        .forEach { (k, v) ->
            if ((magazineFreq[k] ?: 0) < v)
                return false
        }

    return true
}

class RansomNoteTests {
    @Test
    fun `should return true when ransom is in managine`() {
        assertEquals(
            true,
            canConstruct("bg", "efjbdfbdgfjhhaiigfhbaejahgfbbgbjagbddfgdiaigdadhcfcj")
        )
    }

    @Test
    fun `should return false when ransom is NOT in managine`() {
        assertEquals(
            false,
            canConstruct("aa", "ab")
        )
    }
}