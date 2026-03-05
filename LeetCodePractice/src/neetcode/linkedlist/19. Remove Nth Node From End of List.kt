package neetcode.linkedlist

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class RemoveNthNodeFromEndOfBruteForce {
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {

        var cur = head
        var count = 0
        while (cur != null) {
            count++
            cur = cur.next
        }

        val len = count - n
        if (len == 0) return head?.next

        cur = head
        for (i in 0 until count - 1) {
            if (i + 1 == len) {
                cur?.next = cur?.next?.next
                break
            }
            cur = cur?.next
        }

        return head
    }
}

class RemoveNthNodeFromEndTwoPass {
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        val dummy = ListNode(0)
        dummy.next = head
        var slow: ListNode? = dummy
        var fast: ListNode? = dummy

        repeat(n) {
            fast = fast?.next
        }

        while (fast?.next != null) {
            fast = fast?.next
            slow = slow?.next
        }
        slow?.next = slow?.next?.next

        return dummy.next
    }
}

class RemoveNthNodeFromEndStack {
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        val dummy = ListNode(0).also {
            it.next = head
        }
        val stack = ArrayDeque<ListNode>()
        var cur: ListNode? = dummy
        while (cur != null) {
            stack.addLast(cur)
            cur = cur.next
        }
        //remove n nodes => top becomes previous of target
        repeat(n) {
            stack.removeLast()
        }
        val prev = stack.removeLast()
        prev.next = prev.next?.next
        return dummy.next
    }
}

class RemoveNthNodeFromEndOfListTest {
    private fun ListNode?.toList(): List<Int> {
        val out = ArrayList<Int>()
        var cur = this
        while (cur != null) {
            out.add(cur.`val`)
            cur = cur.next
        }
        return out
    }

    private fun buildList(values: List<Int>): ListNode? {
        val dummy = ListNode(0)
        var tail = dummy
        for (v in values) {
            tail.next = ListNode(v)
            tail = tail.next!!
        }
        return dummy.next
    }

    private val impls = listOf(
        //RemoveNthNodeFromEndTwoPass()::removeNthFromEnd,
        RemoveNthNodeFromEndStack()::removeNthFromEnd,
    )

    @Test
    fun `single node remove 1`() {
        val input = listOf(1)
        val expected = emptyList<Int>()

        impls.forEach { f ->
            val head = buildList(input)
            val actual = f(head, 1).toList()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `remove head when n equals length`() {
        val input = listOf(1, 2, 3, 4, 5)
        val expected = listOf(2, 3, 4, 5)

        impls.forEach { f ->
            val head = buildList(input)
            val actual = f(head, 5).toList()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `remove last node`() {
        val input = listOf(1, 2, 3, 4)
        val expected = listOf(1, 2, 3)

        impls.forEach { f ->
            val head = buildList(input)
            val actual = f(head, 1).toList()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `remove middle node`() {
        val input = listOf(1, 2, 3, 4, 5)
        val expected = listOf(1, 2, 3, 5) // remove 4 (n=2)

        impls.forEach { f ->
            val head = buildList(input)
            val actual = f(head, 2).toList()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `two nodes remove first`() {
        val input = listOf(1, 2)
        val expected = listOf(2)

        impls.forEach { f ->
            val head = buildList(input)
            val actual = f(head, 2).toList()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `two nodes remove second`() {
        val input = listOf(1, 2)
        val expected = listOf(1)

        impls.forEach { f ->
            val head = buildList(input)
            val actual = f(head, 1).toList()
            assertEquals(expected, actual)
        }
    }
}