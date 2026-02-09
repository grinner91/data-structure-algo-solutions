package neetcode.stack


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EvaluateReversePolishNotationStack {
    fun evalRPN(tokens: Array<String>): Int {
        val stack = ArrayDeque<Int>()
        //"+", "-", "*", or "/"
        for (t in tokens) {
            when (t) {
                "+" -> {
                    //a + b
                    //1st is right operand
                    val b = stack.removeLast()
                    //2nd is left operand
                    val a = stack.removeLast()
                    stack.addLast(a + b)
                }

                "-" -> {
                    val b = stack.removeLast()
                    val a = stack.removeLast()
                    stack.addLast(a - b)
                }

                "*" -> {
                    val b = stack.removeLast()
                    val a = stack.removeLast()
                    stack.addLast(a * b)
                }

                "/" -> {
                    val b = stack.removeLast()
                    val a = stack.removeLast()
                    stack.addLast(a / b)
                }

                else -> stack.addLast(t.toInt())
            }
        }
        return stack.removeLast()
    }
}

class EvaluateReversePolishNotationRecursive {
    fun evalRPN(tokens: Array<String>): Int {
        val stack = ArrayDeque<Int>()
        var i = tokens.lastIndex
        fun dfs(): Int {
            val t = tokens[i--]
            if (t in listOf("+", "-", "*", "/")) {
                //a + b
                //1st recursion return right operand
                val right = dfs()
                //2nd recursion return left operand
                val left = dfs()
                return when (t) {
                    "+" -> left + right
                    "-" -> left - right
                    "*" -> left * right
                    else -> left / right
                }
            } else {
                return t.toInt()
            }
        }
        return dfs()
    }
}


class EvaluateReversePolishNotationTest {

    private val impls = listOf(
        EvaluateReversePolishNotationStack()::evalRPN,
        EvaluateReversePolishNotationRecursive()::evalRPN
    )

    @Test
    fun example1() {
        val tokens = arrayOf("2", "1", "+", "3", "*")
        impls.forEach { f ->
            assertEquals(9, f(tokens))
        }
    }

    @Test
    fun example2() {
        val tokens = arrayOf("4", "13", "5", "/", "+")
        impls.forEach { f ->
            assertEquals(6, f(tokens))
        }
    }

    @Test
    fun example3() {
        val tokens = arrayOf(
            "10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"
        )
        impls.forEach { f ->
            assertEquals(22, f(tokens))
        }
    }

    @Test
    fun divisionTruncatesTowardZero() {
        val tokens1 = arrayOf("-7", "3", "/") // -2.333 -> -2
        val tokens2 = arrayOf("7", "-3", "/") // -2.333 -> -2
        impls.forEach { f ->
            assertEquals(-2, f(tokens1))
            assertEquals(-2, f(tokens2))
        }
    }

    @Test
    fun singleNumber() {
        val tokens = arrayOf("42")
        impls.forEach { f ->
            assertEquals(42, f(tokens))
        }
    }
}
