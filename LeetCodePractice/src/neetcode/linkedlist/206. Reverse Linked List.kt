package neetcode.linkedlist

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class SolutionReverseLinkedList {
    fun reverseList2(head: ListNode?): ListNode? {
        var prev: ListNode? = null
        var curr: ListNode? = head
        while (curr != null) {
            val next = curr.next
            curr.next = prev
            prev = curr
            curr = next
        }
        return prev
    }

    /**.......................*/
    fun reverseList(head: ListNode?): ListNode? {
        return reverseListRecursion(head, null)
    }

    private fun reverseListRecursion(curr: ListNode?, prev: ListNode?): ListNode? {
        if (curr == null) return prev
        val next = curr.next
        curr.next = prev
        return reverseListRecursion(next, curr)
    }

}

/**.......................*/
class ReverseLinkedListTest {

    private val solution = SolutionReverseLinkedList()

    private fun buildList(vararg values: Int): ListNode? {
        val dummy = ListNode(0)
        var cur = dummy
        for (v in values) {
            cur.next = ListNode(v)
            cur = cur.next!!
        }
        return dummy.next
    }

    private fun toList(head: ListNode?): List<Int> {
        val out = mutableListOf<Int>()
        var cur = head
        while (cur != null) {
            out += cur.`val`
            cur = cur.next
        }
        return out
    }

    @Test
    fun emptyList_returnsNull() {
        val head: ListNode? = null
        val rev = solution.reverseList(head)
        assertEquals(emptyList<Int>(), toList(rev))
    }

    @Test
    fun singleElement_sameList() {
        val head = buildList(42)
        val rev = solution.reverseList(head)
        assertEquals(listOf(42), toList(rev))
    }

    @Test
    fun twoElements_reversed() {
        val head = buildList(1, 2)
        val rev = solution.reverseList(head)
        assertEquals(listOf(2, 1), toList(rev))
    }

    @Test
    fun multipleElements_reversed() {
        val head = buildList(1, 2, 3, 4, 5)
        val rev = solution.reverseList(head)
        assertEquals(listOf(5, 4, 3, 2, 1), toList(rev))
    }

    @Test
    fun withDuplicates_reversed() {
        val head = buildList(1, 1, 2, 2, 3, 3)
        val rev = solution.reverseList(head)
        assertEquals(listOf(3, 3, 2, 2, 1, 1), toList(rev))
    }

    @Test
    fun withNegativesAndZero_reversed() {
        val head = buildList(0, -1, -2, 3)
        val rev = solution.reverseList(head)
        assertEquals(listOf(3, -2, -1, 0), toList(rev))
    }

    @Test
    fun largeList_reversed() {
        val n = 1000
        val head = buildList(*IntArray(n) { it }.toTypedArray().toIntArray())
        val rev = solution.reverseList(head)
        assertEquals((n - 1 downTo 0).toList(), toList(rev))
    }

    @Test
    fun originalHeadBecomesTail_nextIsNull() {
        val head = buildList(1, 2, 3)
        val originalHead = head
        val rev = solution.reverseList(head)
        assertEquals(listOf(3, 2, 1), toList(rev))
        // If your implementation mutates in place (typical), the former head is now tail.
        assertEquals(null, originalHead?.next)
    }
}