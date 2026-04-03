package neetcode.backtracking

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
/*

TC O(4^n * n)

**/
class LetterCombinationsOfPhoneNumberBacktrackDfs {
    fun letterCombinations(digits: String): List<String> {
        if (digits.isEmpty()) return emptyList()

        val phone = arrayOf(
            "", "", "abc", "def", "ghi", "jkl",
            "mno", "pqrs", "tuv", "wxyz"
        )

        val result = mutableListOf<String>()
        val cur = StringBuilder()

        fun backtrack(i: Int) {
            if (i == digits.length) {
                result.add(cur.toString())
                return
            }

            val letters = phone[digits[i] - '0']
            for (l in letters) {
                cur.append(l)
                backtrack(i + 1)
                cur.deleteCharAt(cur.lastIndex)
            }

        }
        backtrack(0)
        return result
    }
}

class LetterCombinationsOfPhoneNumberTest {

    private val impls = listOf<(String) -> List<String>>(
        LetterCombinationsOfPhoneNumberBacktrackDfs()::letterCombinations,

        )

    @Test
    fun emptyInput() {
        impls.forEach { f ->
            assertEquals(emptyList<String>(), f(""))
        }
    }

    @Test
    fun singleDigitTwo() {
        val expected = listOf("a", "b", "c")

        impls.forEach { f ->
            assertEquals(expected, f("2"))
        }
    }

    @Test
    fun singleDigitSeven() {
        val expected = listOf("p", "q", "r", "s")

        impls.forEach { f ->
            assertEquals(expected, f("7"))
        }
    }

    @Test
    fun twoDigits23() {
        val expected = listOf(
            "ad", "ae", "af",
            "bd", "be", "bf",
            "cd", "ce", "cf"
        )

        impls.forEach { f ->
            assertEquals(expected, f("23"))
        }
    }

    @Test
    fun digits79() {
        val expected = listOf(
            "pw", "px", "py", "pz",
            "qw", "qx", "qy", "qz",
            "rw", "rx", "ry", "rz",
            "sw", "sx", "sy", "sz"
        )

        impls.forEach { f ->
            assertEquals(expected, f("79"))
        }
    }

    @Test
    fun threeDigits234_countAndContent() {
        impls.forEach { f ->
            val result = f("234")
            assertEquals(27, result.size)
            assertEquals("adg", result.first())
            assertEquals("cfi", result.last())
        }
    }
}