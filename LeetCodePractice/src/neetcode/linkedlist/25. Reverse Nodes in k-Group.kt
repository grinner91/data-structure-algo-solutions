package neetcode.linkedlist

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ReverseNodesInKGroupIterative {
    private fun getKthNode(head: ListNode?, k: Int): ListNode? {
        var cur = head
        repeat(k) {
            cur = cur?.next ?: return null
        }
        return cur
    }

    private fun reverseList(head: ListNode?): ListNode? {
        var prev: ListNode? = null
        var cur = head
        while (cur != null) {
            val next = cur.next
            cur.next = prev
            prev = cur
            cur = next
        }
        return prev
    }

    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        if (head == null || k == 1) return head

        val dummy = ListNode(0)
        dummy.next = head

        var groupPrev: ListNode = dummy
        while (true) {
            val groupEnd = getKthNode(groupPrev, k) ?: break
            val groupNext = groupEnd.next
            val groupStart = groupPrev.next
            groupEnd.next = null

            val reversedHead = reverseList(groupStart)

            groupPrev.next = reversedHead
            groupStart?.next = groupNext
            groupPrev = groupStart!!
        }

        return dummy.next
    }
}


class ReverseNodesInKGroupRecursion {
    private fun hasKNodes(head: ListNode?, k: Int): Boolean {
        var cur = head
        repeat(k) {
            if (cur == null) return false
            cur = cur?.next
        }
        return true
    }

    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        if (head == null || k <= 1) return head
        if(!hasKNodes(head, k)) return head

        var prev: ListNode? = null
        var cur = head
        var i = 0
        //reverse k nodes
        while (cur != null && i < k) {
            val next = cur.next
            cur.next = prev
            prev = cur
            cur = next
            i++
        }
        head.next = reverseKGroup(cur, k)
        return prev
    }
}

class ReverseNodesInKGroupTest {

    private val impls = listOf(
//        ReverseNodesInKGroupIterative()::reverseKGroup,
        ReverseNodesInKGroupRecursion()::reverseKGroup,
    )

    @Test
    fun example1() {
        impls.forEach { f ->
            val head = buildList(1, 2, 3, 4, 5)
            val actual = f(head, 2)
            assertEquals(listOf(2, 1, 4, 3, 5), toList(actual))
        }
    }

    @Test
    fun example2() {
        impls.forEach { f ->
            val head = buildList(1, 2, 3, 4, 5)
            val actual = f(head, 3)
            assertEquals(listOf(3, 2, 1, 4, 5), toList(actual))
        }
    }

    @Test
    fun kEquals1_returnsOriginalList() {
        impls.forEach { f ->
            val head = buildList(1, 2, 3, 4)
            val actual = f(head, 1)
            assertEquals(listOf(1, 2, 3, 4), toList(actual))
        }
    }

    @Test
    fun kGreaterThanLength_returnsOriginalList() {
        impls.forEach { f ->
            val head = buildList(1, 2, 3)
            val actual = f(head, 5)
            assertEquals(listOf(1, 2, 3), toList(actual))
        }
    }

    @Test
    fun exactMultipleOfK() {
        impls.forEach { f ->
            val head = buildList(1, 2, 3, 4, 5, 6)
            val actual = f(head, 3)
            assertEquals(listOf(3, 2, 1, 6, 5, 4), toList(actual))
        }
    }

    @Test
    fun singleNode() {
        impls.forEach { f ->
            val head = buildList(1)
            val actual = f(head, 2)
            assertEquals(listOf(1), toList(actual))
        }
    }

    @Test
    fun twoNodes_k2() {
        impls.forEach { f ->
            val head = buildList(1, 2)
            val actual = f(head, 2)
            assertEquals(listOf(2, 1), toList(actual))
        }
    }

    @Test
    fun emptyList() {
        impls.forEach { f ->
            val actual = f(null, 2)
            assertEquals(emptyList<Int>(), toList(actual))
        }
    }

    @Test
    fun lastGroupSmallerThanK_staysAsIs() {
        impls.forEach { f ->
            val head = buildList(1, 2, 3, 4, 5, 6, 7)
            val actual = f(head, 4)
            assertEquals(listOf(4, 3, 2, 1, 5, 6, 7), toList(actual))
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
            result += curr.`val`
            curr = curr.next
        }

        return result
    }
}