package neetcode.stack

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MinStackTwoStacks() {
    private val stack = ArrayDeque<Int>()
    private val minStack = ArrayDeque<Int>()
    fun push(`val`: Int) {
        stack.addLast(`val`)
        val minVal = if (minStack.isNotEmpty()) minOf(minStack.last(), `val`) else `val`
        minStack.addLast(minVal)
    }

    fun pop() {
        stack.removeLast()
        minStack.removeLast()
    }

    fun top(): Int {
        return stack.last()
    }

    fun getMin(): Int {
        return minStack.last()
    }
}

class MinStackTest {

    private interface MinStackApi {
        fun push(x: Int)
        fun pop()
        fun top(): Int
        fun getMin(): Int
    }

    private class WrapTwoStacks : MinStackApi {
        private val s = MinStackTwoStacks()
        override fun push(x: Int) = s.push(x)
        override fun pop() = s.pop()
        override fun top(): Int = s.top()
        override fun getMin(): Int = s.getMin()
    }

//    private class WrapEncoded : MinStackApi {
//        private val s = MinStackEncoded()
//        override fun push(x: Int) = s.push(x)
//        override fun pop() = s.pop()
//        override fun top(): Int = s.top()
//        override fun getMin(): Int = s.getMin()
//    }
//
//    private class WrapLinked : MinStackApi {
//        private val s = MinStackLinked()
//        override fun push(x: Int) = s.push(x)
//        override fun pop() = s.pop()
//        override fun top(): Int = s.top()
//        override fun getMin(): Int = s.getMin()
//    }

    private val impls: List<() -> MinStackApi> = listOf(
        ::WrapTwoStacks,
//        ::WrapEncoded,
//        ::WrapLinked
    )

    @Test
    fun exampleCase() {
        impls.forEach { make ->
            val st = make()
            st.push(-2)
            st.push(0)
            st.push(-3)
            assertEquals(-3, st.getMin())
            st.pop()
            assertEquals(0, st.top())
            assertEquals(-2, st.getMin())
        }
    }

    @Test
    fun handlesDuplicates() {
        impls.forEach { make ->
            val st = make()
            st.push(2)
            st.push(2)
            st.push(1)
            st.push(1)
            assertEquals(1, st.getMin())
            st.pop()
            assertEquals(1, st.getMin())
            st.pop()
            assertEquals(2, st.getMin())
        }
    }

    @Test
    fun mixedOperations() {
        impls.forEach { make ->
            val st = make()
            st.push(5)
            st.push(3)
            st.push(7)
            assertEquals(3, st.getMin())
            assertEquals(7, st.top())

            st.pop() // remove 7
            assertEquals(3, st.top())
            assertEquals(3, st.getMin())

            st.pop() // remove 3
            assertEquals(5, st.top())
            assertEquals(5, st.getMin())

            st.push(4)
            assertEquals(4, st.top())
            assertEquals(4, st.getMin())
        }
    }

    @Test
    fun negativeAndRestoreMin() {
        impls.forEach { make ->
            val st = make()
            st.push(0)
            st.push(-1)
            st.push(-2)
            assertEquals(-2, st.getMin())
            st.pop()
            assertEquals(-1, st.getMin())
            st.pop()
            assertEquals(0, st.getMin())
        }
    }
}
