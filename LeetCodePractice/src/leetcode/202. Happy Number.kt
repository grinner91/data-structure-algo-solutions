package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun isHappy(n: Int): Boolean {
    val seen = mutableSetOf<Int>()
    var num = n
    while (num != 1 && !seen.contains(num)) {
        seen.add(num)
        num = num.toString()
            .toCharArray()
            .sumOf { it.digitToInt() * it.digitToInt() }
    }
    return num == 1
}

class IsHappyTests {
    @Test
    fun `should return true`() {
        assertEquals(
            true,
            isHappy(19)
        )
    }

    @Test
    fun `should return false`() {
        assertEquals(
            false,
            isHappy(4)
        )
    }
}
