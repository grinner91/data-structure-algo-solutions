package neetcode.twopointer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ValidPalindromeTwoPointer {
    fun isPalindrome(s: String): Boolean {
        var l = 0
        var r = s.lastIndex
        while (l < r) {
            while (l < r && !s[l].isLetterOrDigit()) l++
            while (l < r && !s[r].isLetterOrDigit()) r--

            if (l < r && s[l].lowercaseChar() != s[r].lowercaseChar())
                return false
            l++
            r--
        }
        return true
    }
}


class ValidPalindromeTest {

    private val solutions = listOf(
        ValidPalindromeTwoPointer()
    )

    @Test
    fun examples() {
        val cases = listOf(
            "A man, a plan, a canal: Panama" to true,
            "race a car" to false,
            " " to true,
        )

        solutions.forEach { sol ->
            cases.forEach { (input, expected) ->
                assertEquals(
                    expected,
                    sol.isPalindrome(input),
                    "Failed for ${sol::class.simpleName} with input: \"$input\""
                )
            }
        }
    }

    @Test
    fun edgeCases() {
        val cases = listOf(
            "" to true,
            "a" to true,
            "aa" to true,
            "ab" to false,
            "0P" to false,
            ".,,," to true,
            "No 'x' in Nixon" to true,
            "Madam, I'm Adam" to true,
        )

        solutions.forEach { sol ->
            cases.forEach { (input, expected) ->
                assertEquals(
                    expected,
                    sol.isPalindrome(input),
                    "Failed for ${sol::class.simpleName} with input: \"$input\""
                )
            }
        }
    }

    @Test
    fun trickyMixed() {
        val cases = listOf(
            "Able was I, ere I saw Elba!" to true,
            "Was it a car or a cat I saw?" to true,
            "12321" to true,
            "1231" to false,
            "a_ba" to true,
            "a_bC" to false,
        )

        solutions.forEach { sol ->
            cases.forEach { (input, expected) ->
                assertEquals(
                    expected,
                    sol.isPalindrome(input),
                    "Failed for ${sol::class.simpleName} with input: \"$input\""
                )
            }
        }
    }
}
