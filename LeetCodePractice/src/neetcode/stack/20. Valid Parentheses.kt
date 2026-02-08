package neetcode.stack

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ValidParenthesesStackMap {
    fun isValid(s: String): Boolean {
        val stack = ArrayDeque<Char>()
        val pairs = mapOf(')' to '(', '}' to '{', ']' to '[')
        s.forEach { ch ->
            val need = pairs[ch]
            if (need != null) {
                if (stack.removeLastOrNull() != need)
                    return false
            } else {
                stack.addLast(ch)
            }
        }
        return stack.isEmpty()
    }
}


class ValidParenthesesStack {
    fun isValid(s: String): Boolean {
        val stack = ArrayDeque<Char>()
        s.forEach { ch ->
            when (ch) {
                '(', '{', '[' -> {
                    stack.addLast(ch)
                }

                ')', '}', ']' -> {
                    if (stack.isEmpty()) return false
                    val open = stack.removeLast()
                    if (!(open == '(' && ch == ')' || open == '{' && ch == '}' || open == '[' && ch == ']'))
                        return false
                }
            }
        }
        return stack.isEmpty()
    }
}

class ValidParenthesesTest {

    private val impls: List<(String) -> Boolean> = listOf(
        //ValidParenthesesStack()::isValid,
        ValidParenthesesStackMap()::isValid
    )

    @Test
    fun examples() {
        checkAll("()" to true)
        checkAll("()[]{}" to true)
        checkAll("(]" to false)
        checkAll("([])" to true)
    }

    @Test
    fun edgeCases() {
        checkAll("" to true)
        checkAll("(" to false)
        checkAll("]" to false)
        checkAll("([)]" to false)
        checkAll("{[]}" to true)
    }

    @Test
    fun nestedAndLonger() {
        checkAll("(((())))" to true)
        checkAll("{[()()]}" to true)
        checkAll("{[()()]}}" to false)
        checkAll("({[]})" to true)
        checkAll("({[})" to false)
    }

    private fun checkAll(vararg cases: Pair<String, Boolean>) {
        for ((input, expected) in cases) {
            impls.forEachIndexed { idx, f ->
                val actual = f(input)
                assertEquals(
                    expected, actual,
                    "impl#$idx failed for input=\"$input\"; expected=$expected, actual=$actual"
                )
            }
        }
    }
}
