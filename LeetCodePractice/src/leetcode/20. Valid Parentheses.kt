package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

fun isValid(parens: String): Boolean {
    val stack = Stack<Char>()
    parens.forEach { current ->
        if (stack.isNotEmpty()) {
            when {
                stack.peek() == '(' && current == ')' -> stack.pop()
                stack.peek() == '{' && current == '}' -> stack.pop()
                stack.peek() == '[' && current == ']' -> stack.pop()
                else -> stack.push(current)
            }
        } else {
            stack.push(current)
        }
    }
    return stack.isEmpty()
}

class IsValiedTests {
    @Test
    fun `should return true1`() {
        assertTrue(
            isValid("()")
        )
    }
    @Test
    fun `should return true2`() {
        assertTrue(
            isValid("()[]{}")
        )
    }
    @Test
    fun `should return true3`() {
        assertEquals(false,
            isValid("(]")
        )
    }
    @Test
    fun `should return true4`() {
        assertTrue(
            isValid("([])")
        )
    }
}