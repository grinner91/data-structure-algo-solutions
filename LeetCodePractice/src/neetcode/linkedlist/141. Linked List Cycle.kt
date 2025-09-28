package neetcode.linkedlist

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SolutionHasCycle {

    fun hasCycle1(head: ListNode?): Boolean {
        var slow = head
        var fast = head
        while (fast?.next != null) {
            slow = slow?.next
            fast = fast.next?.next
            if (slow == fast) {
                return true
            }
        }
        return false
    }

    fun hasCycle(head: ListNode?): Boolean {
        val seen = mutableSetOf<ListNode>()
        var cur = head
        while (cur != null) {
            if (!seen.add(cur)) {
                return true
            }
            cur = cur.next
        }
        return false
    }

}


class LinkedListCycleTest {

    private val sut = SolutionHasCycle()

    // Build a plain (acyclic) list
    private fun buildList(vararg values: Int): ListNode? {
        if (values.isEmpty()) return null
        val head = ListNode(values[0])
        var cur = head
        for (i in 1 until values.size) {
            cur.next = ListNode(values[i])
            cur = cur.next!!
        }
        return head
    }

    /**
     * Build a list and optionally create a cycle by connecting the tail to node at index `pos`.
     * pos == -1 -> no cycle (like LeetCode's convention)
     */
    private fun buildListWithCycle(values: IntArray, pos: Int): ListNode? {
        if (values.isEmpty()) return null
        val head = ListNode(values[0])
        var cur = head
        var cycleNode: ListNode? = if (pos == 0) head else null

        for (i in 1 until values.size) {
            cur.next = ListNode(values[i])
            cur = cur.next!!
            if (i == pos) cycleNode = cur
        }
        if (pos >= 0) cur.next = cycleNode
        return head
    }

    @Test
    fun emptyList_false() {
        val head: ListNode? = null
        assertFalse(sut.hasCycle(head))
    }

    @Test
    fun singleNode_noCycle_false() {
        val head = buildList(1)
        assertFalse(sut.hasCycle(head))
    }

    @Test
    fun singleNode_selfCycle_true() {
        val head = buildList(7)
        head!!.next = head
        assertTrue(sut.hasCycle(head))
    }

    @Test
    fun twoNodes_noCycle_false() {
        val head = buildList(1, 2)
        assertFalse(sut.hasCycle(head))
    }

    @Test
    fun twoNodes_cycleToHead_true() {
        val head = buildListWithCycle(intArrayOf(1, 2), pos = 0)
        assertTrue(sut.hasCycle(head))
    }

    @Test
    fun multiNodes_noCycle_false() {
        val head = buildList(3, 2, 0, -4)
        assertFalse(sut.hasCycle(head))
    }

    @Test
    fun multiNodes_cycleToIndex2_true() {
        // [3,2,0,-4], tail connects to index 2 (value = 0)
        val head = buildListWithCycle(intArrayOf(3, 2, 0, -4), pos = 2)
        assertTrue(sut.hasCycle(head))
    }

    @Test
    fun cycleAtHead_true() {
        // [1,2,3,4,5], tail -> index 0
        val head = buildListWithCycle(intArrayOf(1, 2, 3, 4, 5), pos = 0)
        assertTrue(sut.hasCycle(head))
    }

    @Test
    fun largeList_noCycle_false() {
        val arr = IntArray(1000) { it }
        val head = buildList(*arr.toTypedArray().toIntArray())
        assertFalse(sut.hasCycle(head))
    }

    @Test
    fun largeList_cycleInMiddle_true() {
        val arr = IntArray(1000) { it }
        val head = buildListWithCycle(arr, pos = 500)
        assertTrue(sut.hasCycle(head))
    }
}




