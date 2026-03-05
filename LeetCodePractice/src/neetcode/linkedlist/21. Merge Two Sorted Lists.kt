package neetcode.linkedlist

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test


class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

interface MergeTwoSortedLists {
    fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode?
}

class MergeTwoSortedListsRecursive : MergeTwoSortedLists {
    override fun mergeTwoLists(head1: ListNode?, head2: ListNode?): ListNode? {
        if (head1 == null) return head2
        if (head2 == null) return head1
        return if (head1.`val` <= head2.`val`) {
            head1.next = mergeTwoLists(head1.next, head2)
            head1
        } else {
            head2.next = mergeTwoLists(head1, head2.next)
            head2
        }
    }
}

class MergeTwoSortedListsIterativeDummyNode : MergeTwoSortedLists {
    override fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var cur: ListNode? = dummy
        var n1 = list1
        var n2 = list2
        while (n1 != null && n2 != null) {
            if (n1.`val` <= n2.`val`) {
                cur?.next = n1
                n1 = n1.next
            } else {
                cur?.next = n2
                n2 = n2.next
            }
            cur = cur?.next
        }
        cur?.next = n1 ?: n2
        return dummy.next
    }
}

/* ------------------------------ Test Helpers ------------------------------ */

fun buildList(values: IntArray): ListNode? {
    val dummy = ListNode(0)
    var tail = dummy
    for (v in values) {
        tail.next = ListNode(v)
        tail = tail.next!!
    }
    return dummy.next
}

private fun toIntArray(head: ListNode?): IntArray {
    val out = ArrayList<Int>()
    var cur = head
    while (cur != null) {
        out.add(cur.`val`)
        cur = cur.next
    }
    return out.toIntArray()
}

/**
 * Deep copy is important because solutions typically *reuse and mutate* nodes.
 * We want each implementation to receive a fresh input list.
 */
private fun cloneList(head: ListNode?): ListNode? {
    if (head == null) return null
    val dummy = ListNode(0)
    var tail = dummy
    var cur = head
    while (cur != null) {
        tail.next = ListNode(cur.`val`)
        tail = tail.next!!
        cur = cur.next
    }
    return dummy.next
}

/* --------------------------------- Tests --------------------------------- */


class MergeTwoSortedListsTest {

    private val impls = listOf(
        MergeTwoSortedListsIterativeDummyNode()::mergeTwoLists,
        MergeTwoSortedListsRecursive()::mergeTwoLists
    )

    @Test
    fun example1() {
        val a = buildList(intArrayOf(1, 2, 4))
        val b = buildList(intArrayOf(1, 3, 4))
        val expected = intArrayOf(1, 1, 2, 3, 4, 4)

        impls.forEach { f ->
            val res = f(cloneList(a), cloneList(b))
            assertArrayEquals(expected, toIntArray(res))
        }
    }

    @Test
    fun bothNull() {
        impls.forEach { f ->
            val res = f(null, null)
            assertArrayEquals(intArrayOf(), toIntArray(res))
        }
    }

    @Test
    fun oneNull() {
        val a = buildList(intArrayOf(0, 2, 5))
        impls.forEach { f ->
            assertArrayEquals(intArrayOf(0, 2, 5), toIntArray(f(cloneList(a), null)))
            assertArrayEquals(intArrayOf(0, 2, 5), toIntArray(f(null, cloneList(a))))
        }
    }

    @Test
    fun duplicatesAndNegatives() {
        val a = buildList(intArrayOf(-3, -1, -1, 2))
        val b = buildList(intArrayOf(-2, -1, 3))
        val expected = intArrayOf(-3, -2, -1, -1, -1, 2, 3)

        impls.forEach { f ->
            val res = f(cloneList(a), cloneList(b))
            assertArrayEquals(expected, toIntArray(res))
        }
    }

    @Test
    fun alreadyOrderedByList1ThenList2() {
        val a = buildList(intArrayOf(1, 2, 3))
        val b = buildList(intArrayOf(4, 5, 6))
        val expected = intArrayOf(1, 2, 3, 4, 5, 6)

        impls.forEach { f ->
            val res = f(cloneList(a), cloneList(b))
            assertArrayEquals(expected, toIntArray(res))
        }
    }

    @Test
    fun interleaving() {
        val a = buildList(intArrayOf(1, 3, 5, 7))
        val b = buildList(intArrayOf(2, 4, 6))
        val expected = intArrayOf(1, 2, 3, 4, 5, 6, 7)

        impls.forEach { f ->
            val res = f(cloneList(a), cloneList(b))
            assertArrayEquals(expected, toIntArray(res))
        }
    }
}