package neetcode.linkedlist

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AddTwoNumbersIterative {
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var d1: ListNode? = l1
        var d2: ListNode? = l2
        var carry = 0
        var cur: ListNode = dummy

        while (d1 != null || d2 != null || carry != 0) {
            val sum = (d1?.`val` ?: 0) + (d2?.`val` ?: 0) + carry
            carry = sum / 10
            cur.next = ListNode(sum % 10)

            cur = cur.next!!
            d1 = d1?.next
            d2 = d2?.next
        }
        return dummy.next
    }
}


class AddTwoNumbersRecursion {
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {

        fun addDigits(d1: ListNode?, d2: ListNode?, carry: Int): ListNode? {
            if (d1 == null && d2 == null && carry == 0) return null
            
            val sum = (d1?.`val` ?: 0) + (d2?.`val` ?: 0) + carry
            val car = sum / 10
            val node = ListNode(sum % 10)
            node.next = addDigits(d1?.next, d2?.next, car)

            return node
        }

        return addDigits(l1, l2, 0)
    }
}

class AddTwoNumbersTest {

    private val impls = listOf<(ListNode?, ListNode?) -> ListNode?>(
        AddTwoNumbersIterative()::addTwoNumbers,
        AddTwoNumbersRecursion()::addTwoNumbers,
    )

    @Test
    fun `example 1`() {
        val l1 = buildList(2, 4, 3)
        val l2 = buildList(5, 6, 4)

        impls.forEach { f ->
            assertEquals(listOf(7, 0, 8), toList(f(l1, l2)))
        }
    }

    @Test
    fun `example 2`() {
        val l1 = buildList(0)
        val l2 = buildList(0)

        impls.forEach { f ->
            assertEquals(listOf(0), toList(f(l1, l2)))
        }
    }

    @Test
    fun `example 3`() {
        val l1 = buildList(9, 9, 9, 9, 9, 9, 9)
        val l2 = buildList(9, 9, 9, 9)

        impls.forEach { f ->
            assertEquals(listOf(8, 9, 9, 9, 0, 0, 0, 1), toList(f(l1, l2)))
        }
    }

    @Test
    fun `different lengths without final carry`() {
        val l1 = buildList(1, 8)
        val l2 = buildList(0)

        impls.forEach { f ->
            assertEquals(listOf(1, 8), toList(f(l1, l2)))
        }
    }

    @Test
    fun `carry propagates across all nodes`() {
        val l1 = buildList(9, 9, 9)
        val l2 = buildList(1)

        impls.forEach { f ->
            assertEquals(listOf(0, 0, 0, 1), toList(f(l1, l2)))
        }
    }

    @Test
    fun `one list is null`() {
        val l1 = null
        val l2 = buildList(3, 1, 4)

        impls.forEach { f ->
            assertEquals(listOf(3, 1, 4), toList(f(l1, l2)))
        }
    }

    @Test
    fun `both lists are null`() {
        val l1 = null
        val l2 = null

        impls.forEach { f ->
            assertEquals(emptyList<Int>(), toList(f(l1, l2)))
        }
    }

    private fun buildList(vararg values: Int): ListNode? {
        val dummy = ListNode(0)
        var tail = dummy

        for (value in values) {
            tail.next = ListNode(value)
            tail = tail.next!!
        }

        return dummy.next
    }

    private fun toList(head: ListNode?): List<Int> {
        val result = mutableListOf<Int>()
        var curr = head

        while (curr != null) {
            result.add(curr.`val`)
            curr = curr.next
        }

        return result
    }
}