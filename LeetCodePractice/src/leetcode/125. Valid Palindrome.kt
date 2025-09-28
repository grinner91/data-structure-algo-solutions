package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

fun isPalindrome(str: String): Boolean {
    return str
        .filter { it.isLetterOrDigit() }
        .lowercase(Locale.getDefault())
        .run { equals(reversed()) }
}


class PalindromeTest {
    @Test
    fun testPalindromeWithSpecialChars() {
        assertEquals(
            true,
            isPalindrome("A man, a plan, a canal: Panama")
        )
    }
    @Test
    fun testPalindrome2() {
        assertEquals(
            false,
            isPalindrome("race a car")
        )
    }
    @Test
    fun testPalindromeWithSpecialChars2() {
        assertEquals(
            true,
            isPalindrome(" ")
        )
    }
}


