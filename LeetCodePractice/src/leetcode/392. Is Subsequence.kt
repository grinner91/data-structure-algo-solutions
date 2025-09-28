package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun isSubsequence(sub: String, str: String): Boolean {
    var j = 0
    return str
        .forEach { ch ->
            if (j < sub.length && sub[j] == ch) j++
            if(sub.length == j) return true
        }
        .run { sub.length == j }
}

class TestSubsequence {
    @Test
    fun `should return true when subsequence exists`(): Unit {
        assertEquals(
            true,
            isSubsequence("abc", "ahbgdcsdf")
        )
    }

    @Test
    fun `should return true when subsequence 2`(): Unit {
        assertEquals(
            false,
            isSubsequence("axc", "ahbgdc")
        )
    }
}