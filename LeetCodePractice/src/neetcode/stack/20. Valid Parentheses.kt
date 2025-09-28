package neetcode.stack

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun isValid(s: String): Boolean {
    val stack = ArrayDeque<Char>()
    val paris = mapOf(')' to '(', ']' to '[', '}' to '{')
    s.forEach { ch ->
        val need = paris[ch]
        if (need != null) {
            if (stack.removeLastOrNull() != need)
                return false
        } else {
            stack.addLast(ch)
        }
    }
    return stack.isEmpty()
}

class IsValidParentheses {
    @Test
    fun test1() {
        assertEquals(true, isValid("[]"))
    }

    @Test
    fun test2() {
        assertEquals(true, isValid("([{}])"))
    }

    @Test
    fun test3() {
        assertEquals(false, isValid("[(])"))
    }
}