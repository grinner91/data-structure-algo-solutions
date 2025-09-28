package neetcode.linkedlist

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SolutionMergeTwoLists {
    fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
        var l1 = list1
        var l2 = list2
        val dummy = ListNode(0)
        var curr = dummy
        while (l1 != null && l2 != null) {
            if (l1.`val` <= l2.`val`) {
                curr.next = l1
                l1 = l1.next
            } else {
                curr.next = l2
                l2 = l2.next
            }
            curr = curr.next!!
        }
        curr.next = l1 ?: l2
        return dummy.next
    }
}

class MergeTwoSortedListsSimpleTest {

    private val sut = SolutionMergeTwoLists() // your implementation with mergeTwoLists(l1, l2)

    // Minimal helpers
    private fun build(vararg values: Int): ListNode? {
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
    fun both_empty() {
        val merged = sut.mergeTwoLists(null, null)
        assertEquals(emptyList<Int>(), toList(merged))
    }

    @Test
    fun left_empty() {
        val merged = sut.mergeTwoLists(null, build(1, 3, 4))
        assertEquals(listOf(1, 3, 4), toList(merged))
    }

    @Test
    fun right_empty() {
        val merged = sut.mergeTwoLists(build(1, 3, 5), null)
        assertEquals(listOf(1, 3, 5), toList(merged))
    }

    @Test
    fun interleaving() {
        val merged = sut.mergeTwoLists(build(1, 2, 4), build(1, 3, 4))
        assertEquals(listOf(1, 1, 2, 3, 4, 4), toList(merged))
    }

    @Test
    fun with_duplicates() {
        val merged = sut.mergeTwoLists(build(1, 1, 1), build(1, 1))
        assertEquals(listOf(1, 1, 1, 1, 1), toList(merged))
    }

    @Test
    fun negatives_and_zero() {
        val merged = sut.mergeTwoLists(build(-3, -1, 2), build(-2, 0, 3))
        assertEquals(listOf(-3, -2, -1, 0, 2, 3), toList(merged))
    }

    @Test
    fun uneven_lengths() {
        val merged = sut.mergeTwoLists(build(1, 2, 2, 10), build(2, 3))
        assertEquals(listOf(1, 2, 2, 2, 3, 10), toList(merged))
    }
}